package no.ntnu.idi.stud.bank.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idi.stud.bank.dto.TransactionDTO;
import no.ntnu.idi.stud.bank.model.Account;
import no.ntnu.idi.stud.bank.model.Transaction;
import no.ntnu.idi.stud.bank.repository.AccountRepository;
import no.ntnu.idi.stud.bank.repository.TransactionRepository;
import no.ntnu.idi.stud.bank.service.TransactionService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

  private TransactionRepository transactionRepository;
  private AccountRepository accountRepository;

  public TransactionDTO saveTransaction(TransactionDTO transactionRequest){

    Optional<Account> debtorAccount =
        accountRepository.findById(transactionRequest.getDebtorBBAN());
    if (debtorAccount.isEmpty()) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(404));
    }
    Optional<Account> creditorAccount =
        accountRepository.findById(transactionRequest.getCreditorBBAN());
    if (creditorAccount.isEmpty()) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(404));
    }

    Transaction savedTransaction = new Transaction();
    savedTransaction.setDebtorAccount(debtorAccount.get());
    savedTransaction.setCreditorAccount(creditorAccount.get());
    savedTransaction.setAmount(transactionRequest.getAmount());

    transactionRepository.save(savedTransaction);

    return transactionRequest;
  }


}
