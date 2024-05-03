package no.ntnu.idi.stud.savingsapp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.service.impl.BudgetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TestPropertySource(locations = "classpath:application-h2.yml")
@SpringBootTest
public class BudgetServiceTest {

	@Autowired
	private BudgetServiceImpl budgetService;

	private User user;

	private Budget expectedBudget;

	private Budget expectedBudget2;

	@BeforeEach
	void init() {

		user = new User();
		user.setFirstName("User");
		user.setLastName("User");
		user.setEmail("user@example.com");
		user.setPassword("SomeEncryptedPassword1");
		user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		user.setRole(Role.USER);

		expectedBudget = new Budget();
		expectedBudget.setBudgetName("TestBudget1");
		expectedBudget.setUser(user);
		expectedBudget.setBudgetAmount(new BigDecimal(1000));
		expectedBudget.setExpenseAmount(new BigDecimal(200));
		expectedBudget.setCreatedAt(new Timestamp(System.currentTimeMillis()));

		expectedBudget2 = new Budget();
		expectedBudget2.setBudgetName("TestBudget2");
		expectedBudget2.setUser(user);
		expectedBudget2.setBudgetAmount(new BigDecimal(50));
		expectedBudget2.setExpenseAmount(new BigDecimal(10));
		expectedBudget2.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}

	@Test
	void shouldCreateBudget() {
		Budget createdBudget = budgetService.createBudget(expectedBudget2);

		assertThat(createdBudget).isNotNull();
		assertThat(createdBudget.getBudgetName()).isEqualTo(expectedBudget2.getBudgetName());
	}

}
