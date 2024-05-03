package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.dto.goal.GroupUserDTO;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.goal.Group;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing user goals.
 */
@Service
public interface GoalService {

  /**
   * Creates and persists a new goal for a specific user.
   * This method is responsible for setting the initial parameters of the goal, associating it with a user,
   * and saving it to the database.
   *
   * @param goal The goal object containing the initial data for the goal.
   * @param userId The ID of the user for whom the goal is being created.
   * @return The newly created and persisted Goal object.
   */
  Goal createGoal(Goal goal, List<GroupUserDTO> GroupUsers, long userId);

  /**
   * Retrieves all goals associated with a specific user.
   * This method is used to fetch all goals that belong to a user, based on the user's ID.
   *
   * @param userId The ID of the user whose goals are to be retrieved.
   * @return A list of Goal objects associated with the specified user.
   *         The list may be empty if the user has no goals.
   */
  List<Goal> getGoals(long userId);

  /**
   * Retrieves a goal associated with a specific user.
   *
   * @param goalId The ID of the user whose goals are to be retrieved.
   * @return A goal object associated with the specified user.
   */
  Goal getGoal(long goalId);

  /**
   * Retrieves a group associated with a specific goal
   *
   * @param goalId the goal that the group contains
   * @return A group object associated with the specified goal
   */
  Group getGroup(Long goalId);
}
