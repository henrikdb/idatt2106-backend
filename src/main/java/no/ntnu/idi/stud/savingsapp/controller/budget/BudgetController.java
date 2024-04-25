package no.ntnu.idi.stud.savingsapp.controller.budget;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetRequestDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetResponseDTO;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
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

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BudgetResponseDTO>> getBudgetsByUser(@AuthenticationPrincipal AuthIdentity identity) {
    List<Budget> budgets = budgetService.findBudgetsByUserId(identity.getId());
    List<BudgetResponseDTO> budgetDTOs = new ArrayList<>();
    for (Budget budget : budgets) {
      budgetDTOs.add(modelMapper.map(budget, BudgetResponseDTO.class));
    }
    return ResponseEntity.ok(budgetDTOs);
  }

  @GetMapping(value = "/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)

  public ResponseEntity<BudgetResponseDTO> getBudget(@PathVariable long budgetId) {
    Budget budget = budgetService.findBudgetById(budgetId);
    BudgetResponseDTO response = modelMapper.map(budget, BudgetResponseDTO.class);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Budget> createBudget(@AuthenticationPrincipal AuthIdentity identity, @RequestBody BudgetRequestDTO request) {
    Budget budget = modelMapper.map(request, Budget.class);
    budget.setUser(userService.findById(identity.getId()));
    budget.setCreatedAt(Timestamp.from(Instant.now()));
    budgetService.createBudget(budget);
    return ResponseEntity.ok().build();
  }

  @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseDTO> updateBudget(@RequestBody BudgetResponseDTO request) {
    Budget budget = modelMapper.map(request, Budget.class);
    budgetService.updateBudget(budget);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/delete/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetResponseDTO> deleteBudget(@PathVariable long budgetId) {
    budgetService.deleteBudgetById(budgetId);
    return ResponseEntity.ok().build();
  }
}
