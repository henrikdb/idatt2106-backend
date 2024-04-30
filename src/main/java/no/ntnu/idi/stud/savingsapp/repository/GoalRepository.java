package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for handling operations on the {@link Goal} entities.
 */
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

  /**
   * Retrieves all goals associated with a specified user ID.
   * This method is typically used to fetch all goals created by a specific user, enabling the application to
   * display a user-specific list of goals.
   *
   * @param userId The ID of the user whose goals are to be retrieved.
   * @return A list of {@link Goal} entities that belong to the specified user. The list can be empty if no goals exist.
   */
  List<Goal> findByUser_Id(long userId);

  /**
   * Finds a goal that contains a specific challenge identified by its ID.
   * This method is useful for operations that require verification of goal ownership or details when handling a specific challenge.
   *
   * @param id The ID of the challenge whose goal needs to be identified.
   * @return An {@link Optional} containing the {@link Goal} if found, or an empty Optional if no such goal exists.
   */
  Optional<Goal> findByChallenges_Id(long id);
}
