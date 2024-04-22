package no.ntnu.idi.stud.bank.service;

import java.util.List;
import no.ntnu.idi.stud.bank.model.Account;

public interface AccountService {

  List<Account> getAccountsByBankProfileId(Long ssn);

}
