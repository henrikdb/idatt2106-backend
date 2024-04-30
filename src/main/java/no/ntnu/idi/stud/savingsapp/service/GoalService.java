package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoalService {

  Goal createGoal(Goal goal, long userId);

  List<Goal> getGoalList(long userId);
}
