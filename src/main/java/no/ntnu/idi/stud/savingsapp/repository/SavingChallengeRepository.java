package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link SavingChallenge} entities.
 */
@Repository
public interface SavingChallengeRepository extends JpaRepository<SavingChallenge, Long> {

  /**
   * Find all the challenges with the same challenge type.
   *
   * @param challengeType The type of challenge to search for.
   * @return A list of {@link SavingChallenge SavingChallenges}
   */
  List<SavingChallenge> findAllByChallengeType(ChallengeType challengeType);

}
