package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;

import no.ntnu.idi.stud.savingsapp.model.savings.Goal;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl {
  @Autowired
  private UserService userService;

  @Autowired
  private GoalRepository goalRepository;

  @Autowired
  private GoalChallengeSerivceImpl goalChallengeSerivce;

  public Goal createGoal (Long userID, String name, String description, int targetAmount, Timestamp targetDate) {
    Goal goal = new Goal();
    goal.setGoalName(name);
    goal.setCreatedAt(Timestamp.from(Instant.now()));
    goal.setDescription(description);
    goal.setTargetAmount(targetAmount);
    goal.setTargetDate(targetDate);
    goal.setCreator(userService.findById(userID));

    goal.setChallenges(goalChallengeSerivce.generateSavingGoalChallenges(goal, userService.findById(userID)));

    return goalRepository.save(goal);
  }
}
