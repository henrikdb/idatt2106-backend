package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link Goal} entities
 */
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

	List<Goal> findByParticipants_User_Id(Long userId);

}
