package no.ntnu.idi.stud.savingsapp.controller.budget;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetListedDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetRequest;
import no.ntnu.idi.stud.savingsapp.exception.budget.BudgetNotFoundException;
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

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Budget>> getBudgetsByUser(@AuthenticationPrincipal AuthIdentity identity) {
    List<Budget> budgetList = budgetService.findBudgetsByUserId(identity.getId());
    return ResponseEntity.ok(budgetList);
  }

  @GetMapping(value = "/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetDTO> getBudget(@PathVariable long budgetId) {
    return null;
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Budget> createBudget(@AuthenticationPrincipal AuthIdentity identity) {


    Budget budget = new Budget(null,
        userService.findById(identity.getId()),
        Timestamp.from(Instant.now()),
        "April 2097",
        BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0));
    return ResponseEntity.ok(budget);
  }

  @PostMapping(value = "/update/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetDTO> updateBudget(@PathVariable long budgetId, @RequestBody BudgetRequest budgetRequest) {
    return null;
  }

  @GetMapping(value = "/delete/{budgetId}", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BudgetDTO> deleteBudget(@PathVariable long budgetId) {
    return null;
  }
}
