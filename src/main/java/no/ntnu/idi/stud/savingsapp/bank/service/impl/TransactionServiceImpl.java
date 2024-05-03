package no.ntnu.idi.stud.savingsapp.bank.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.bank.dto.TransactionDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.bank.model.Transaction;
import no.ntnu.idi.stud.savingsapp.bank.repository.AccountRepository;
import no.ntnu.idi.stud.savingsapp.bank.repository.TransactionRepository;
import no.ntnu.idi.stud.savingsapp.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementation of the {@link TransactionService} interface for transaction related operations.
 */
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;

  /**
   * Performs and saves a transaction between two accounts.
   *
   * @param transactionRequest The transaction to be performed, containing the bban of the creditor
   *                           and debitor accounts in addition to the amount that is being
   *                           transferred.
   */
  @Override
  public void saveTransaction(TransactionDTO transactionRequest){

    boolean negativeTransactionAmount = (transactionRequest.getAmount().signum() == -1);

    if (negativeTransactionAmount) {
      log.error("[TransactionService:saveTransaction] Negative transfer amount: {}", transactionRequest.getAmount());
      throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Negative transfer amount");
    }

    Optional<Account> debtorAccount =
        accountRepository.findAccountByBban(transactionRequest.getDebtorBBAN());
    if (debtorAccount.isEmpty()) {
      log.error("[TransactionService:saveTransaction] Debtor account not found: {}", transactionRequest.getDebtorBBAN());
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Debtor account not found");
    }
    Optional<Account> creditorAccount =
        accountRepository.findAccountByBban(transactionRequest.getCreditorBBAN());
    if (creditorAccount.isEmpty()) {
      log.error("[TransactionService:saveTransaction] Creditor account not found: {}", transactionRequest.getDebtorBBAN());
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Creditor account not found");
    }

    boolean negativeFunds = ((debtorAccount.get().getBalance().subtract(transactionRequest.getAmount())).signum() == -1);
    if (negativeFunds) {
      log.error("[TransactionService:saveTransaction] Insufficient funds: {}", transactionRequest.getAmount());
      throw new ResponseStatusException(HttpStatusCode.valueOf(402), "Insufficient funds");
    }

    try {
      Transaction savedTransaction = new Transaction();
      savedTransaction.setDebtorAccount(debtorAccount.get());
      savedTransaction.setCreditorAccount(creditorAccount.get());
      savedTransaction.setAmount(transactionRequest.getAmount());

      BigDecimal debtorBalance = (
          debtorAccount.get().getBalance()).subtract(transactionRequest.getAmount()
      );

      BigDecimal creditorBalance = (
          creditorAccount.get().getBalance().add(transactionRequest.getAmount())
      );

      accountRepository.updateBalance(debtorBalance, debtorAccount.get().getBban());
      accountRepository.updateBalance(creditorBalance, creditorAccount.get().getBban());
      transactionRepository.save(savedTransaction);
      log.info("[TransactionService:saveTransaction] saved transaction: {}", savedTransaction);
    } catch (Exception e) {
      log.error("[TransactionService:saveTransaction] Transaction failed");
      throw new ResponseStatusException(HttpStatusCode.valueOf(400), e.getMessage());
    }
  }
}
