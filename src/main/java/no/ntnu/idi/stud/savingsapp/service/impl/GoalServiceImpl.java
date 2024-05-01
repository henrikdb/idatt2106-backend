package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import no.ntnu.idi.stud.savingsapp.exception.goal.ChallengeNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.GoalNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import no.ntnu.idi.stud.savingsapp.service.GoalService;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing goals.
 */
@Service
public class GoalServiceImpl implements GoalService {

  @Autowired
  private UserService userService;

  @Autowired
  private GoalRepository goalRepository;

  @Autowired
  private ChallengeService challengeService;

  /**
   * Creates a new goal for a specific user and generates associated challenges.
   *
   * @param goal The goal to be created, containing initial data.
   * @param userId The ID of the user for whom the goal is being created.
   * @return The newly created Goal, now populated with generated challenges and persisted in the database.
   */
  @Override
  public Goal createGoal(Goal goal, long userId) {
    User user = userService.findById(userId);
    goal.setCreatedAt(Timestamp.from(Instant.now()));
    goal.setUser(user);
    List<Challenge> challenges = challengeService.generateChallenges(goal, user);
    goal.setChallenges(challenges);
    return goalRepository.save(goal);
  }

  /**
   * Retrieves all goals associated with a given user ID.
   *
   * @param userId The ID of the user whose goals are to be retrieved.
   * @return A list of Goals associated with the specified user.
   */
  @Override
  public List<Goal> getGoals(long userId) {
    return goalRepository.findByUser_Id(userId);
  }

  @Override
  public Goal getGoal(long goalId) {
    Optional<Goal> optionalGoal = goalRepository.findById(goalId);
    if (optionalGoal.isPresent()) {
      return optionalGoal.get();
    } else {
      throw new GoalNotFoundException();
    }
  }
}
