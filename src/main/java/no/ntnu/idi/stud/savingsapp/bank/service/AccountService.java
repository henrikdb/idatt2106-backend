package no.ntnu.idi.stud.savingsapp.bank.service;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountRequestDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import org.springframework.stereotype.Service;

/**
 * Service interface for bank account related operations.
 */
@Service
public interface AccountService {

  /**
   * Get a list of accounts of a bank profile by providing the associated id of the bank profile.
   *
   * @param id The id of the bank profile.
   * @return   A list of accounts.
   */
  List<Account> getAccountsByBankProfileId(Long id);

  /**
   * Get a list of accounts of a bank profile by providing the associated Social Security Number
   * of the bank profile.
   *
   * @param ssn The Social Security Number of the bank profile.
   * @return   A list of accounts.
   */
  List<Account> getAccountsBySsn(Long ssn);

  /**
   * Saves an account to the database.
   *
   * @param accountRequestDto         The DTO containing the bank profile id.
   * @return                          The saved account.
   */
  AccountResponseDTO saveAccount(AccountRequestDTO accountRequestDto);

  /**
   * Get an account given the Basic Bank Account Number
   *
   * @param bban  The Basic Bank Account Number belonging to the account.
   * @return      The account if it exists, if not: return empty.
   */
  Account getAccountByBban(Long bban);

}
