package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.exception.budget.BudgetNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.budget.ExpenseNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import no.ntnu.idi.stud.savingsapp.model.budget.Expense;
import no.ntnu.idi.stud.savingsapp.repository.BudgetRepository;
import no.ntnu.idi.stud.savingsapp.repository.ExpenseRepository;
import no.ntnu.idi.stud.savingsapp.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Implementation of BudgetService to manage budgets.
 */
@Service
@Slf4j
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	/**
	 * Retrieves a list of budgets associated with a user id.
	 * @param userId the if of the user
	 * @return a list of budgets.
	 */
	@Override
	public List<Budget> findBudgetsByUserId(Long userId) {
		return budgetRepository.findBudgetsByUserId(userId);
	}

	/**
	 * Retrieves a list of expenses associated with a budget id.
	 * @param budgetId the id of the budget
	 * @return a list of expenses.
	 */
	@Override
	public List<Expense> findExpensesByBudgetId(Long budgetId) {
		return expenseRepository.findExpensesByBudgetId(budgetId);
	}

	/**
	 * Creates a new budget.
	 * @param budget the budget to create
	 * @return the created budget.
	 * @throws DataIntegrityViolationException if an error occurred.
	 */
	@Override
	public Budget createBudget(Budget budget) {
		budget.setCreatedAt(Timestamp.from(Instant.now()));
		try {
			return budgetRepository.save(budget);
		}
		catch (DataIntegrityViolationException e) {
			log.error("[BudgetServiceImpl:createBudget] Error while creating budget");
			throw new DataIntegrityViolationException("Error creating budget");
		}
	}

	/**
	 * Updates an existing budget.
	 * @param budget the budget to update
	 * @return the updated budget.
	 * @throws DataIntegrityViolationException if an error occurred.
	 */
	@Override
	public Budget updateBudget(Budget budget) {
		try {
			return budgetRepository.save(budget);
		}
		catch (DataIntegrityViolationException e) {
			log.error("[BudgetServiceImpl:updateBudget] Error while updating budget");
			throw new DataIntegrityViolationException("Error updating budget");
		}
	}

	/**
	 * Retrieves a budget by its id.
	 * @param budgetId the id of the budget
	 * @return the budget with the specified id.
	 * @throws BudgetNotFoundException if budget is not found.
	 */
	@Override
	public Budget findBudgetById(Long budgetId) {
		Optional<Budget> optionalBudget = budgetRepository.findBudgetById(budgetId);
		if (optionalBudget.isPresent()) {
			return optionalBudget.get();
		}
		else {
			log.error("[BudgetServiceImpl:findBudgetById] Budget does not exists, id: {}", budgetId);
			throw new BudgetNotFoundException();
		}
	}

	/**
	 * Deletes a budget by its id.
	 * @param budgetId The id of the budget to delete.
	 * @throws BudgetNotFoundException If budget is not found.
	 */
	@Override
	public void deleteBudgetById(Long budgetId) {
		Optional<Budget> optionalBudget = budgetRepository.findBudgetById(budgetId);
		if (optionalBudget.isPresent()) {
			budgetRepository.delete(optionalBudget.get());
		}
		else {
			log.error("[BudgetServiceImpl:deleteBudgetById] Budget does not exists, id: {}", budgetId);
			throw new BudgetNotFoundException();
		}
	}

	/**
	 * Creates a new expense.
	 * @param expense the expense to create
	 * @return the created expense
	 * @throws DataIntegrityViolationException if an error occurred.
	 */
	@Override
	public Expense createExpense(Expense expense) {
		try {
			return expenseRepository.save(expense);
		}
		catch (DataIntegrityViolationException e) {
			log.error("[BudgetServiceImpl:createExpanse] Error while creating expense");
			throw new DataIntegrityViolationException("Error creating expense");
		}
	}

	/**
	 * Updates an existing expense.
	 * @param expense The expense to update
	 * @return The updated expense
	 * @throws DataIntegrityViolationException If an error occurred.
	 */
	@Override
	public Expense updateExpense(Expense expense) {
		try {
			return expenseRepository.save(expense);
		}
		catch (DataIntegrityViolationException e) {
			log.error("[BudgetServiceImpl:updateExpanse] Error while updating expense");
			throw new DataIntegrityViolationException("Error updating expense");
		}
	}

	/**
	 * Retrieves an expense by its id.
	 * @param expenseId The id of the expense
	 * @return The expense with the specified id.
	 * @throws ExpenseNotFoundException If expense is not found.
	 */
	@Override
	public Expense findExpenseById(Long expenseId) {
		Optional<Expense> optionalExpense = expenseRepository.findExpenseById(expenseId);
		if (optionalExpense.isPresent()) {
			return optionalExpense.get();
		}
		else {
			log.error("[BudgetServiceImpl:findExpenseById] expense is not found, id: {}", expenseId);
			throw new ExpenseNotFoundException();
		}
	}

	/**
	 * Deletes an expense by its id.
	 * @param expenseId The id of the expense to delete.
	 * @throws ExpenseNotFoundException If expense is not found.
	 */
	@Override
	public void deleteExpenseById(Long expenseId) {
		Optional<Expense> optionalExpense = expenseRepository.findExpenseById(expenseId);
		if (optionalExpense.isPresent()) {
			expenseRepository.delete(optionalExpense.get());
		}
		else {
			log.error("[BudgetServiceImpl:deleteExpenseById] expense is not found, id: {}", expenseId);
			throw new ExpenseNotFoundException();
		}
	}

}
