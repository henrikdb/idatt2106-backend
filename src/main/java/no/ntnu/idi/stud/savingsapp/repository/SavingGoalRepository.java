package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link SavingGoal} entities
 */
@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {

}
