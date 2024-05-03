package no.ntnu.idi.stud.savingsapp.bank.repository;

import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.bank.model.BankProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link BankProfile} entities.
 */
@Repository
public interface BankProfileRepository extends JpaRepository<BankProfile, Long> {

	/**
	 * Get the bank profile associated with a Social Security Number.
	 * @param ssn The Social Security Number of the user.
	 * @return The user if it exists, if not: returns empty.
	 */
	Optional<BankProfile> findBySsn(Long ssn);

}
