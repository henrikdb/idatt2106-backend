package no.ntnu.idi.stud.savingsapp.controller.budget;

import no.ntnu.idi.stud.savingsapp.UserUtil;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
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
  @WithMockUser()
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
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdAt").value("2024-04-26T07:56:18.172+00:00"))
        // test second budget in list
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].budgetName").value("April 2024"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].budgetAmount").value(10000.00))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].expenseAmount").value(5000.00))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].createdAt").value("2024-04-26T07:56:18"
            + ".172+00:00"));
      }

}
