package no.ntnu.idi.stud.bank.service.impl;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.bank.dto.AccountRequestDTO;
import no.ntnu.idi.stud.bank.dto.AccountResponseDTO;
import no.ntnu.idi.stud.bank.model.Account;
import no.ntnu.idi.stud.bank.model.BankProfile;
import no.ntnu.idi.stud.bank.repository.AccountRepository;
import no.ntnu.idi.stud.bank.repository.BankProfileRepository;
import no.ntnu.idi.stud.bank.service.AccountService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {
  private AccountRepository accountRepository;
  private BankProfileRepository bankProfileRepository;

  public List<Account> getAccountsByBankProfileId(Long ssn) {
    return accountRepository.findAllByBankProfileId(ssn);
  }

  /**
   * Saves an account to the database.
   *
   * @param accountRequestDto The DTO containing the bank profile id.
   * @return The saved
   * @throws ResponseStatusException
   */
  public AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDto) throws ResponseStatusException {
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    try {
      Optional<BankProfile> profile = bankProfileRepository.findById(accountRequestDto.getBankProfileId());
      if (profile.isEmpty()) {
        throw new ResponseStatusException(HttpStatusCode.valueOf(404));
      }
      Account newAccount = new Account();
      newAccount.setBankProfile(profile.get());
      accountRepository.saveAccount(newAccount);
      accountResponseDTO.setBalance(newAccount.getBalance());
      accountResponseDTO.setBankProfileId(newAccount.getBankProfile().getId());
    } catch (ResponseStatusException e) {
      throw new ResponseStatusException(e.getStatusCode());
    }
    return accountResponseDTO;
  }
}
