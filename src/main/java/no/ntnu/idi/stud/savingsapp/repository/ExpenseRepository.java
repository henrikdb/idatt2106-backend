package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.budget.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the {@link Expense expense} entity.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	/**
	 * Finds an expense by the expense's id.
	 * @param id The belonging id of the expense
	 * @return An optional containing the expense if found, otherwise empty.
	 */
	Optional<Expense> findExpenseById(Long id);

	/**
	 * Deletes an expense by the budget's id.
	 * @param id The belonging id of the expense
	 */
	void deleteExpenseById(Long id);

	/**
	 * Finds all expenses by a budget's id.
	 * @param id The budget's id
	 * @return A list of expenses.
	 */
	List<Expense> findExpensesByBudgetId(Long id);

}
