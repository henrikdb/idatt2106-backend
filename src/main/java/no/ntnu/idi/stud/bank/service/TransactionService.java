package no.ntnu.idi.stud.bank.service;

import no.ntnu.idi.stud.bank.dto.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

  public TransactionDTO saveTransaction(TransactionDTO transaction);
}
