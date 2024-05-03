package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing challenges associated with goals.
 */
@Service
public interface ChallengeService {

  /**
   * Generates a list of challenges for a specified goal based on the user's preferences and the goal's details.
   * This method should consider user-specific settings and the duration of the goal to tailor challenges accordingly.
   *
   * @param goal The goal for which challenges are being generated.
   * @param user The user associated with the goal, whose preferences should influence the challenges.
   * @return A list of generated Challenge objects that are tailored to the user's preferences and the goal's duration.
   */
  List<Challenge> generateChallenges(Goal goal, User user);

  /**
   * Updates the progress of a specific challenge on a given day by recording the amount achieved.
   * This method is responsible for ensuring that the progress update is valid and that the user has permission to update the specified challenge.
   *
   * @param userId The ID of the user attempting to update the challenge.
   * @param id The unique identifier of the challenge being updated.
   * @param day The specific day of the challenge for which progress is being updated.
   * @param amount The amount or value achieved on the specified day.
   * @throws IllegalArgumentException if the user does not have permission to update the challenge or if the day or amount parameters are invalid.
   */
  void updateProgress(long userId, long id, int day, BigDecimal amount);

  /**
   * Updates the saving amount for a specific challenge identified by its ID.
   * This method allows modifying the potential saving target for a given challenge, ensuring that
   * users can adjust their saving goals as needed.
   *
   * @param userId The ID of the user who owns the challenge. This is used to verify ownership
   *               and permission to update the challenge.
   * @param id     The ID of the challenge whose saving amount is to be updated.
   * @param amount The new saving amount to be set for the challenge. This amount should
   *               reflect the new target savings the user aims to achieve.
   */
  void updateSavingAmount(long userId, long id, BigDecimal amount);

  /**
   * Replaces that challenge in a given goal identified by the challenge ID.
   * This method is useful for letting the user change out challenges they know
   * they will not be able to do
   *
   * @param userId the ID of the user who sends the request
   * @param challengeId The ID of the challenge that will be replaced
   * @return The updated goal containing the new challenge
   */
  Challenge regenerateChallenge(long userId, long challengeId);
}
