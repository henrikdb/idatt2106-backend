package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.savings.DifficultyLevel;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
  //List<SavingChallenge> findAllByChallengeType(ChallengeType challengeType);


  /**
   * Find all SavingChallenges that have a given difficulty level.
   *
   * @param difficultyLevel The difficulty level of the challenges to be found.
   * @return A list of {@link SavingChallenge SavingChallenges}.
   */
  /*@Query("SELECT sc.* FROM saving_challenge sc "
      + "JOIN difficulty_level dl "
      + "ON sc.difficulty_level_id = dl.difficulty_level_id "
      + "WHERE dl.difficulty_level_id = :difLevel")*/
  //List<SavingChallenge> findAllByDifficultyLevel(@Param("difLevel") DifficultyLevel difficultyLevel);

  // TODO find all challenges in a list of challenges that are of a difficulty level.

  /**
   * Find the difficulty level of a {@link SavingChallenge}.
   *
   * @param savingChallengeId The id of the {@link SavingChallenge} who's {@link DifficultyLevel}
   *                          should be found.
   * @return The {@link DifficultyLevel} of the {@link SavingChallenge} if found, if not return
   * empty.
   */
  /*@Query("SELECT dl.* FROM difficulty_level dl JOIN saving_challenge sc "
      + "ON dl.difficulty_level_id = sc.difficulty_level_id"
      + "WHERE sc.saving_challenge_id = :savingChallengeId")
  Optional<DifficultyLevel> findDifficultyLevelBySavingChallengeId(
      @Param("savingChallengeId")
      Long savingChallengeId
  );*/

}
