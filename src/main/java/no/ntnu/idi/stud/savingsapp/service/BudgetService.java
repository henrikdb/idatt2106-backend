package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import no.ntnu.idi.stud.savingsapp.model.budget.Expense;
import org.springframework.stereotype.Service;

/**
 * Service interface for budget-related operations
 */
@Service
public interface BudgetService {
  /**
   * Retrieves a list of budgets associated with a user id.
   *
   * @param userId the if of the user
   * @return a list of budgets.
   */
  List<Budget> findBudgetsByUserId(Long userId);

  /**
   * Retrieves a list of expenses associated with a budget id.
   *
   * @param budgetId the id of the budget
   * @return a list of expenses.
   */
  List<Expense> findExpensesByBudgetId(Long budgetId);

  /**
   * Creates a new budget.
   *
   * @param budget the budget to create
   * @return the created budget.
   */
  Budget createBudget(Budget budget);

  /**
   * Updates an existing budget.
   *
   * @param budget the budget to update
   * @return the updated budget.
   */
  Budget updateBudget(Budget budget);

  /**
   * Retrieves a budget by its id.
   *
   * @param budgetId The id of the budget
   * @return The budget with the specified id.
   */
  Budget findBudgetById(Long budgetId);

  /**
   * Deletes a budget by its id.
   *
   * @param budgetId the id of the budget to delete
   */
  void deleteBudgetById(Long budgetId);

  /**
   * Creates a new expense.
   *
   * @param expense the expense to create
   * @return the created expense
   */
  Expense createExpense(Expense expense);

  /**
   * Updates an existing expense.
   *
   * @param expense the expense to update
   * @return the updated expense
   */
  Expense updateExpense(Expense expense);

  /**
   * Retrieves an expense by its id.
   *
   * @param expenseId the id of the expense
   * @return the expense with the specified id.
   */
  Expense findExpenseById(Long expenseId);

  /**
   * Deletes an expense by its id.
   *
   * @param expenseId the id of the expense to delete.
   */
  void deleteExpenseById(Long expenseId);
}
