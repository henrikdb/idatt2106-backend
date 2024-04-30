package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChallengeService {
  List<Challenge> generateSavingGoalChallenges (Goal goal, User user);

  void updateProgress(long userId, long id, int day);
}
