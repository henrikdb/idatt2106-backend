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

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  /**
   * Get all accounts that belong to a social security number.
   *
   * @param bankProfileId The id of the bank profile that belongs to the desired accounts.
   * @return A list of accounts.
   */
  @Query("SELECT a FROM Account a WHERE a.bankProfile.id = :bankProfileId")
  List<Account> findAllByBankProfileId(@Param("bankProfileId") Long bankProfileId);

  List<Account> findAllByBankProfileSsn(Long ssn);

  @Modifying
  @Transactional
  @Query(value = "UPDATE account a SET a.balance = :amount WHERE a.bban = :bban", nativeQuery = true)
  int updateBalance(@Param("amount") BigDecimal amount,@Param("bban") Long bban);

}
