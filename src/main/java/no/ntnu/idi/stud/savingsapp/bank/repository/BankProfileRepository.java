package no.ntnu.idi.stud.savingsapp.bank.repository;

import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.bank.model.BankProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankProfileRepository extends JpaRepository<BankProfile, Long> {


  Optional<BankProfile> findBySsn(Long ssn);

}
