package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the {@link Budget budget} entity.
 */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

	/**
	 * Finds a budget by the budget's id.
	 * @param id The belonging id of the budget
	 * @return An optional containing the budget if found, otherwise empty.
	 */
	Optional<Budget> findBudgetById(Long id);

	/**
	 * Deletes a budget by the budget's id.
	 * @param id The belonging id of the budget
	 */
	void deleteBudgetById(Long id);

	/**
	 * Finds all budget by a user's id.
	 * @param id The user's id
	 * @return A list of budgets.
	 */
	List<Budget> findBudgetsByUserId(Long id);

}
