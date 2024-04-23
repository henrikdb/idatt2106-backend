package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoalChallenge;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoalChallengeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link SavingGoalChallenge} entities.
 */
@Repository
public interface SavingGoalChallengeRepository extends JpaRepository<SavingGoalChallenge,
    SavingGoalChallengeId> {

  /**
   * Find all {@link SavingGoalChallenge SavingGoalChallenges} from the id of the goal.
   *
   * @param goalId The id of the goal used to find the {@link SavingGoalChallenge}.
   * @return The list of {@link SavingGoalChallenge SavingGoalChallenges}.
   */
  List<SavingGoalChallenge> findAllByGoalId(Long goalId);
}
