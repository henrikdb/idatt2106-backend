package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

@Service
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public List<Budget> findBudgetsByUserId(Long userId) {
		return budgetRepository.findBudgetsByUserId(userId);
	}

	@Override
	public List<Expense> findExpensesByBudgetId(Long budgetId) {
		return expenseRepository.findExpensesByBudgetId(budgetId);
	}

	@Override
	public Budget createBudget(Budget budget) {
		budget.setCreatedAt(Timestamp.from(Instant.now()));
		try {
			return budgetRepository.save(budget);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Error creating budget");
		}
	}

	@Override
	public Budget updateBudget(Budget budget) {
		try {
			return budgetRepository.save(budget);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Error updating budget");
		}
	}

	@Override
	public Budget findBudgetById(Long budgetId) {
		Optional<Budget> optionalBudget = budgetRepository.findBudgetById(budgetId);
		if (optionalBudget.isPresent()) {
			return optionalBudget.get();
		}
		else {
			throw new BudgetNotFoundException();
		}
	}

	@Override
	public void deleteBudgetById(Long budgetId) {
		Optional<Budget> optionalBudget = budgetRepository.findBudgetById(budgetId);
		if (optionalBudget.isPresent()) {
			budgetRepository.delete(optionalBudget.get());
		}
		else {
			throw new BudgetNotFoundException();
		}
	}

	@Override
	public Expense createExpense(Expense expense) {
		try {
			return expenseRepository.save(expense);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Error creating expense");
		}
	}

	@Override
	public Expense updateExpense(Expense expense) {
		try {
			return expenseRepository.save(expense);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Error updating expense");
		}
	}

	@Override
	public Expense findExpenseById(Long expenseId) {
		Optional<Expense> optionalExpense = expenseRepository.findExpenseById(expenseId);
		if (optionalExpense.isPresent()) {
			return optionalExpense.get();
		}
		else {
			throw new ExpenseNotFoundException();
		}
	}

	@Override
	public void deleteExpenseById(Long expenseId) {
		Optional<Expense> optionalExpense = expenseRepository.findExpenseById(expenseId);
		if (optionalExpense.isPresent()) {
			expenseRepository.delete(optionalExpense.get());
		}
		else {
			throw new ExpenseNotFoundException();
		}
	}

}
