package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.goal.DifficultyLevel;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Challenge} entities.
 */
@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

  /**
   * Find all the challenges with the same challenge type.
   *
   * @param challengeType The type of challenge to search for.
   * @return A list of {@link Challenge SavingChallenges}
   */
  //List<SavingChallenge> findAllByChallengeType(ChallengeType challengeType);


  /**
   * Find all SavingChallenges that have a given difficulty level.
   *
   * @param difficultyLevel The difficulty level of the challenges to be found.
   * @return A list of {@link Challenge SavingChallenges}.
   */
  /*@Query("SELECT sc.* FROM saving_challenge sc "
      + "JOIN difficulty_level dl "
      + "ON sc.difficulty_level_id = dl.difficulty_level_id "
      + "WHERE dl.difficulty_level_id = :difLevel")*/
  //List<SavingChallenge> findAllByDifficultyLevel(@Param("difLevel") DifficultyLevel difficultyLevel);

  // TODO find all challenges in a list of challenges that are of a difficulty level.

  /**
   * Find the difficulty level of a {@link Challenge}.
   *
   * @param savingChallengeId The id of the {@link Challenge} who's {@link DifficultyLevel}
   *                          should be found.
   * @return The {@link DifficultyLevel} of the {@link Challenge} if found, if not return
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
