package no.ntnu.idi.stud.savingsapp.controller.budget;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetRequestDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetResponseDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.ExpenseRequestDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.ExpenseResponseDTO;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import no.ntnu.idi.stud.savingsapp.model.budget.Expense;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.BudgetService;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling budget related requests.
 */
@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/budget")
@EnableAutoConfiguration
@Tag(name = "User")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Retrieves all the budgets belonging to authenticated user.
	 * @param identity The security context of the authenticated user.
	 * @return ResponseEntity containing the list of BudgetResponseDTO of the
	 * authenticated user.
	 * @apiNote This endpoint is used to fetch all the budget's related to the
	 * authenticated user. It uses the user's ID stored in the authentication principal to
	 * filter for budgets.
	 */
	@Operation(summary = "Get the list of budgets",
			description = "Get all budgets related to " + "the authenticated user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got budgets") })
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BudgetResponseDTO>> getBudgetsByUser(@AuthenticationPrincipal AuthIdentity identity) {
		List<Budget> budgets = budgetService.findBudgetsByUserId(identity.getId());
		List<BudgetResponseDTO> budgetDTOs = new ArrayList<>();
		for (Budget budget : budgets) {
			budgetDTOs.add(modelMapper.map(budget, BudgetResponseDTO.class));
		}
		return ResponseEntity.ok(budgetDTOs);
	}

	/**
	 * Retrieves a budget by its id.
	 * @param budgetId The id of the budget to be retrieved.
	 * @return ResponseEntity containing the BudgetResponseDTO.
	 */
	@Operation(summary = "Get the budget", description = "Get budget by its id ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got budget"),
			@ApiResponse(responseCode = "500", description = "Budget is not found"), })
	@GetMapping(value = "/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BudgetResponseDTO> getBudget(@PathVariable long budgetId) {
		Budget budget = budgetService.findBudgetById(budgetId);
		BudgetResponseDTO response = modelMapper.map(budget, BudgetResponseDTO.class);
		return ResponseEntity.ok(response);
	}

	/**
	 * Creates a new budget .
	 * @param identity The security context of the authenticated user.
	 * @param request The budget request.
	 * @return ResponseEntity.
	 */
	@Operation(summary = "Create a new budget", description = "Create a new budget with based on the budget request")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created new budget") })
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Budget> createBudget(@AuthenticationPrincipal AuthIdentity identity,
			@RequestBody BudgetRequestDTO request) {
		Budget budget = modelMapper.map(request, Budget.class);
		budget.setUser(userService.findById(identity.getId()));
		budget.setCreatedAt(Timestamp.from(Instant.now()));
		budgetService.createBudget(budget);
		return ResponseEntity.ok().build();
	}

	/**
	 * Updates a budget.
	 * @param identity The security context of the authenticated user.
	 * @param request The budget request.
	 * @return ResponseEntity.
	 */
	@Operation(summary = "Updates a budget", description = "Updates a budget based on the budget request")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated budget"),
			@ApiResponse(responseCode = "500", description = "Budget is not found") })
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateBudget(@AuthenticationPrincipal AuthIdentity identity,
			@RequestBody BudgetResponseDTO request) {
		Budget budget = modelMapper.map(request, Budget.class);
		budget.setUser(userService.findById(identity.getId()));
		budgetService.updateBudget(budget);
		return ResponseEntity.ok().build();
	}

	/**
	 * Deletes a budget.
	 * @param budgetId The budget's id.
	 * @return ResponseEntity.
	 */
	@Operation(summary = "Deletes a budget", description = "Deletes a budget based on provided budget id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully deleted budget"),
			@ApiResponse(responseCode = "500", description = "Budget is not found") })
	@GetMapping(value = "/delete/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteBudget(@PathVariable long budgetId) {
		budgetService.deleteBudgetById(budgetId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Creates/Updates an expense.
	 * @param budgetId The budget id to the budget where the expense is stored.
	 * @return ResponseEntity.
	 */
	@Operation(summary = "Created/Updates an expense",
			description = "Creates/Updates a budget based on the budget request")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated budget"),
			@ApiResponse(responseCode = "500", description = "Budget is not found"),
			@ApiResponse(responseCode = "500", description = "Error updating expense") })
	@PostMapping(value = "/update/expense/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateExpense(@PathVariable Long budgetId, @RequestBody ExpenseRequestDTO request) {
		Budget budget = budgetService.findBudgetById(budgetId);
		Expense expense = modelMapper.map(request, Expense.class);
		expense.setBudget(budget);
		budgetService.createExpense(expense);
		return ResponseEntity.ok(request);
	}

	/**
	 * Retrieves an expense by its id.
	 * @param expenseId The id of the expense to be retrieved.
	 * @return ResponseEntity containing the ExpenseResponseDTO.
	 */
	@Operation(summary = "Get the expense", description = "Get expense by its id ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got expense"),
			@ApiResponse(responseCode = "500", description = "Expense is not found"), })
	@GetMapping(value = "/expense/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExpenseResponseDTO> getExpense(@PathVariable Long expenseId) {
		Expense expense = budgetService.findExpenseById(expenseId);
		ExpenseResponseDTO response = modelMapper.map(expense, ExpenseResponseDTO.class);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves all expenses belonging to a budget.
	 * @param budgetId The id of the budget where the expenses are stored.
	 * @return ResponseEntity containing the list of ExpenseResponseDTO of the budget.
	 */
	@Operation(summary = "Get the list of budgets",
			description = "Get all budgets related to " + "the authenticated user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got expenses") })
	@GetMapping(value = "/expenses/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExpenseResponseDTO>> getExpenses(@PathVariable Long budgetId) {
		List<Expense> expenses = budgetService.findExpensesByBudgetId(budgetId);
		List<ExpenseResponseDTO> expenseDTOs = new ArrayList<>();
		for (Expense expense : expenses) {
			expenseDTOs.add(modelMapper.map(expense, ExpenseResponseDTO.class));
		}
		return ResponseEntity.ok(expenseDTOs);
	}

	/**
	 * Deletes an expense.
	 * @param expenseId The expense's id.
	 * @return ResponseEntity.
	 */
	@Operation(summary = "Deletes an expense", description = "Deletes an expense based on provided expense id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully deleted expense"),
			@ApiResponse(responseCode = "500", description = "Expense is not found") })
	@GetMapping(value = "/delete/expense/{expenseId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteExpense(@PathVariable Long expenseId) {
		budgetService.deleteExpenseById(expenseId);
		return ResponseEntity.ok().build();
	}

}
