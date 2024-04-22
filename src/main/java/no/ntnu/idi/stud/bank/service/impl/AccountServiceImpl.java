package no.ntnu.idi.stud.bank.service.impl;

import java.util.List;
import no.ntnu.idi.stud.bank.model.Account;
import no.ntnu.idi.stud.bank.repository.AccountRepository;
import no.ntnu.idi.stud.bank.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
  private AccountRepository accountRepository;

  public List<Account> getAccountsByBankProfileId(Long ssn) {
    return accountRepository.findAllByBankProfileId(ssn);
  }
}
