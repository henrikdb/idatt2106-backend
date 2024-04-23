package no.ntnu.idi.stud.savingsapp.bank.service;

import no.ntnu.idi.stud.savingsapp.bank.dto.TransactionDTO;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

  void saveTransaction(TransactionDTO transaction);
}
