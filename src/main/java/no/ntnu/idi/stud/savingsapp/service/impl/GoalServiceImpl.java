package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import no.ntnu.idi.stud.savingsapp.service.GoalService;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {

  @Autowired
  private UserService userService;

  @Autowired
  private GoalRepository goalRepository;

  @Autowired
  private ChallengeService challengeService;

  @Override
  public Goal createGoal(Goal goal, long userId) {
    User user = userService.findById(userId);
    goal.setCreatedAt(Timestamp.from(Instant.now()));
    goal.setUser(user);
    List<Challenge> challenges = challengeService.generateChallenges(goal, user);
    goal.setChallenges(challenges);
    return goalRepository.save(goal);
  }

  public List<Goal> getGoalList(long userId) {
    return goalRepository.findByUser_Id(userId);
  }
}
