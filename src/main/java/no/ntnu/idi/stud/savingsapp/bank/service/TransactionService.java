package no.ntnu.idi.stud.savingsapp.bank.service;

import no.ntnu.idi.stud.savingsapp.bank.dto.TransactionDTO;
import org.springframework.stereotype.Service;

/**
 * Service interface for bank transaction related operations.
 */
@Service
public interface TransactionService {

  /**
   * Performs and saves a transaction between two accounts.
   *
   * @param transactionRequest The transaction to be performed, containing the bban of the creditor
   *                           and debitor accounts in addition to the amount that is being
   *                           transferred.
   */
  void saveTransaction(TransactionDTO transactionRequest);
}
