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
}
