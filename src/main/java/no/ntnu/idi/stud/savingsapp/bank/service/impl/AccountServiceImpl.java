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

/**
 * Implementation of the {@link AccountService} interface for account related operations.
 */
@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private BankProfileRepository bankProfileRepository;

  /**
   * Get a list of accounts of a bank profile by providing the associated id of the bank profile.
   *
   * @param id The id of the bank profile.
   * @return   A list of accounts.
   */
  @Override
  public List<Account> getAccountsByBankProfileId(Long id) {
    List<Account> accountList;
    Optional<BankProfile> bankProfile = bankProfileRepository.findById(id);
    if (bankProfile.isEmpty()) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Bank profile not found");
    }
      accountList = accountRepository.findAllByBankProfileId(id);
    return accountList;
  }

  /**
   * Get a list of accounts of a bank profile by providing the associated Social Security Number
   * of the bank profile.
   *
   * @param ssn The Social Security Number of the bank profile.
   * @return   A list of accounts.
   */
  @Override
  public List<Account> getAccountsBySsn(Long ssn) {
    List<Account> accountList;
    Optional<BankProfile> bankProfile = bankProfileRepository.findBySsn(ssn);
    if (bankProfile.isEmpty()) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Bank profile not found");
    }
    accountList = accountRepository.findAllByBankProfileSsn(ssn);
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
      Optional<BankProfile> profile = bankProfileRepository.findBySsn(accountRequestDto.getSsn());
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

  /**
   * Get an account given the Basic Bank Account Number
   *
   * @param bban  The Basic Bank Account Number belonging to the account.
   * @return      The account if it exists, if not: return empty.
   */
  @Override
  public Account getAccountByBban(Long bban) {
    Optional<Account> account = accountRepository.findAccountByBban(bban);
    if (account.isEmpty()) {
      throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Account not found");
    }
    return account.get();
  }
}
