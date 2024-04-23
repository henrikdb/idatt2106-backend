package no.ntnu.idi.stud.savingsapp.bank.service.impl;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountRequestDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.bank.model.BankProfile;
import no.ntnu.idi.stud.savingsapp.bank.repository.AccountRepository;
import no.ntnu.idi.stud.savingsapp.bank.repository.BankProfileRepository;
import no.ntnu.idi.stud.savingsapp.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private BankProfileRepository bankProfileRepository;

  @Override
  public List<Account> getAccountsByBankProfileId(Long ssn) {
    List<Account> accountList;
    try {
      accountList = accountRepository.findAllByBankProfileId(ssn);

    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Bank profile not found");
    }
    return accountList;
  }

  /**
   * Saves an account to the database.
   *
   * @param accountRequestDto         The DTO containing the bank profile id.
   * @return                          The saved account.
   * @throws ResponseStatusException  if the profile was not found, or the account could not be
   *                                  created
   */
  @Override
  public AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDto) throws ResponseStatusException {
    AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
    try {
      Optional<BankProfile> profile = bankProfileRepository.findById(accountRequestDto.getBankProfileId());
      if (profile.isEmpty()) {
        throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Bank profile not found");
      }
      Account newAccount = new Account();
      newAccount.setBankProfile(profile.get());
      accountRepository.save(newAccount);
      accountResponseDTO.setBalance(newAccount.getBalance());
      accountResponseDTO.setBankProfileId(newAccount.getBankProfile().getId());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(400), e.getMessage());
    }
    return accountResponseDTO;
  }
}
