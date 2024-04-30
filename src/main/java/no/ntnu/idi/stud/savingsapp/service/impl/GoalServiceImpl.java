package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
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
  private ChallengeServiceImpl goalChallengeSerivce;


  public Goal createGoal (Goal goal, Long userID) {
    goal.setChallenges(goalChallengeSerivce.generateSavingGoalChallenges(goal, userService.findById(userID)));
    goal.setCreatedAt(Timestamp.from(Instant.now()));

    goal.setUser(userService.findById(userID));

    System.out.println(goal);

    return goalRepository.save(goal);
  }

  public List<Goal> getGoalList (Long userID) {
    return goalRepository.findByUser_Id(userID);
  }
}
