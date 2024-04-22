package no.ntnu.idi.stud.bank.repository;

import java.util.List;
import no.ntnu.idi.stud.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  /**
   * Get all accounts that belong to a social security number.
   *
   * @param bankProfileId The id of the bank profile that belongs to the desired accounts.
   * @return A list of accounts.
   */
  @Query("SELECT a.* FROM account a WHERE a.bank_profile_id = :bankProfileId")
  List<Account> findAllByBankProfileId(@Param("bankProfileId") Long bankProfileId);

}
