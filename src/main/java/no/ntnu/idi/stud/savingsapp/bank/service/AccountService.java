package no.ntnu.idi.stud.savingsapp.bank.service;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountRequestDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {

  List<Account> getAccountsByBankProfileId(Long ssn);

  List<Account> getAccountsBySsn(Long ssn);

  AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDto);

}
