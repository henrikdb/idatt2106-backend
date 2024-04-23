package no.ntnu.idi.stud.savingsapp.bank.repository;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository interface for {@link Account} entities.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  /**
   * Get all accounts that belong to a user with a specified id.
   *
   * @param bankProfileId The id of the bank profile that belongs to the desired accounts.
   * @return A list of accounts.
   */
  @Query("SELECT a FROM Account a WHERE a.bankProfile.id = :bankProfileId")
  List<Account> findAllByBankProfileId(@Param("bankProfileId") Long bankProfileId);

  /**
   * Get all accounts that belong to a user with a specified social security number.
   *
   * @param ssn The Social Security Number of the user.
   * @return    A list of accounts.
   */
  List<Account> findAllByBankProfileSsn(Long ssn);

  /**
   * Update the balance of an account.
   *
   * @param amount The new balance of the account.
   * @param bban   The Basic Bank Account Number, specifying which account is updated.
   * @return       The number of affected rows.
   */
  @Modifying
  @Transactional
  @Query(value = "UPDATE account a SET a.balance = :amount WHERE a.bban = :bban", nativeQuery = true)
  int updateBalance(@Param("amount") BigDecimal amount,@Param("bban") Long bban);

}
