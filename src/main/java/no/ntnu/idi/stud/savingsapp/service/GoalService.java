package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoalService {

	/**
	 * @param userID
	 * @param goal
	 * @return the goal that has been created
	 */
	Goal createGoal(Goal goal, Long userID);

	List<Goal> getGoalList(Long userID);

}
