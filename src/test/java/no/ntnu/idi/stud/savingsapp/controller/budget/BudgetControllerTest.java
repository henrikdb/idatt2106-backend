package no.ntnu.idi.stud.savingsapp.controller.budget;

import java.math.BigDecimal;
import no.ntnu.idi.stud.savingsapp.JsonUtil;
import no.ntnu.idi.stud.savingsapp.UserUtil;
import no.ntnu.idi.stud.savingsapp.dto.budget.BudgetRequestDTO;
import no.ntnu.idi.stud.savingsapp.dto.budget.ExpenseRequestDTO;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BudgetControllerTest {

  @Autowired
  private MockMvc mvc;

  private User user;


  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(1L);
    user.setRole(Role.USER);
    user.setEmail("user@example.com");
    user.setFirstName("User");
    user.setLastName("User");
  }


  @Test
  void shouldGetAllBudgetsByUserId() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/budget")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        // test first budget in list
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].budgetName").value("March 2024"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].budgetAmount").value(20000.00))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].expenseAmount").value(5000.00))
        // test second budget in list
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].budgetName").value("April 2024"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].budgetAmount").value(10000.00))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].expenseAmount").value(5000.00));
  }

  @Test
  @WithMockUser
  void shouldGetBudgetByBudgetId() throws Exception {
  mvc.perform(MockMvcRequestBuilders.get("/api/budget/1"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
      .andExpect(MockMvcResultMatchers.jsonPath("$.budgetName").value("April 2024"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.budgetAmount").value(10000.00))
      .andExpect(MockMvcResultMatchers.jsonPath("$.expenseAmount").value(5000.00));
  }

  @Test
  void shouldCreateBudget() throws Exception {
    BudgetRequestDTO budgetRequestDTO = new BudgetRequestDTO();
    budgetRequestDTO.setBudgetName("Test Budget");
    budgetRequestDTO.setBudgetAmount(new BigDecimal(200));
    budgetRequestDTO.setExpenseAmount(new BigDecimal(100));

    mvc.perform(MockMvcRequestBuilders
        .post("/api/budget/create")
        .with(SecurityMockMvcRequestPostProcessors
            .authentication(UserUtil.getAuthentication(user)))
        .content(JsonUtil.toJson(budgetRequestDTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser
  void shouldUpdateBudgetGivenBudgetId() throws Exception {
    BudgetRequestDTO updatedBudget = new BudgetRequestDTO();
    updatedBudget.setBudgetName("Update test");
    updatedBudget.setBudgetAmount(new BigDecimal(20));
    updatedBudget.setExpenseAmount(new BigDecimal(10));

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.budgetName").value("April 2024"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.budgetAmount").value(10000.00))
        .andExpect(MockMvcResultMatchers.jsonPath("$.expenseAmount").value(5000.00));

    mvc.perform(MockMvcRequestBuilders.post("/api/budget/update/1")
        .content(JsonUtil.toJson(updatedBudget))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.budgetName").value("Update test"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.expenseAmount").value(10.00))
        .andExpect(MockMvcResultMatchers.jsonPath("$.budgetAmount").value(20.00));
  }

  @Test
  @WithMockUser
  void shouldDeleteBudgetByBudgetId() throws Exception {

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/delete/1"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/1"))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());

  }

  @Test
  @WithMockUser
  void shouldUpdateExpenseByBudgetId() throws Exception {
    ExpenseRequestDTO expenseRequestDTO = new ExpenseRequestDTO();
    expenseRequestDTO.setExpenseId(1L);
    expenseRequestDTO.setAmount(new BigDecimal(10));
    expenseRequestDTO.setDescription("Updated description");

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/expense/1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Rent"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("8000.00"));

    mvc.perform(MockMvcRequestBuilders.post("/api/budget/update/expense/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(expenseRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/expense/1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated description"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("10"));
  }

  @Test
  @WithMockUser
  void shouldGetExpenseByExpenseId() throws Exception{
    mvc.perform(MockMvcRequestBuilders.get("/api/budget/expense/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Rent"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("8000.00"));
  }

  @Test
  @WithMockUser
  void shouldGetAllExpensesByBugetId() throws Exception{
    mvc.perform(MockMvcRequestBuilders.get("/api/budget/expenses/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        // check first expense
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].description").value("Rent"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].amount").value("8000.00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].budgetId").value(1))
        // check second expense
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Cheese"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].amount").value("1000.00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].budgetId").value(1))
        // check third expense
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Milk"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount").value("1000.00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].budgetId").value(1));
  }

  @Test
  @WithMockUser
  void shouldDeleteExpenseByExpenseId() throws Exception{

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/expense/1"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/delete/expense/1"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/budget/expense/1"))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
  }
}
