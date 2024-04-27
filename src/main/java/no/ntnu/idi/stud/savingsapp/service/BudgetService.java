package no.ntnu.idi.stud.savingsapp.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import no.ntnu.idi.stud.savingsapp.model.budget.Expense;
import org.springframework.stereotype.Service;

/**
 * Service interface for budget-related operations
 */
@Service
public interface BudgetService {

	List<Budget> findBudgetsByUserId(Long userId);

	List<Expense> findExpensesByBudgetId(Long budgetId);

	Budget createBudget(Budget budget);

	Budget updateBudget(Budget budget);

	Budget findBudgetById(Long budgetId);

	void deleteBudgetById(Long budgetId);

	Expense createExpense(Expense expense);

	Expense updateExpense(Expense expense);

	Expense findExpenseById(Long expenseId);

	void deleteExpenseById(Long expenseId);

}
