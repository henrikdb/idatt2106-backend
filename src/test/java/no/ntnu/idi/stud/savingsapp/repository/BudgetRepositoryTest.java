package no.ntnu.idi.stud.savingsapp.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.budget.Budget;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-h2.yml")
public class BudgetRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BudgetRepository budgetRepository;

	private Budget expectedBudget;

	private Budget expectedBudget2;

	private User user;

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

		// Persist all users
		entityManager.persist(user);
		entityManager.persist(expectedBudget);
		entityManager.persist(expectedBudget2);

		entityManager.flush();
	}

	@AfterEach
	void tearDown() {
		entityManager.clear();
	}

	@Test
	void shouldGetBudgetById() {
		Optional<Budget> budgetResult = budgetRepository.findBudgetById(expectedBudget.getId());
		assertThat(budgetResult.isPresent());
		assertThat(budgetResult.get().getBudgetAmount()).isSameAs(expectedBudget.getBudgetAmount());
		assertThat(budgetResult.get().getBudgetName()).isEqualTo(expectedBudget.getBudgetName());
		assertThat(budgetResult.get().getUser()).isSameAs(expectedBudget.getUser());
		assertThat(budgetResult.get().getExpenseAmount()).isSameAs(expectedBudget.getExpenseAmount());
		assertThat(budgetResult.get().getCreatedAt()).isSameAs(expectedBudget.getCreatedAt());
		assertThat(budgetResult.get().getId()).isEqualTo(expectedBudget.getId());
	}

	@Test
	void shouldDeleteBudgetById() {
		budgetRepository.deleteBudgetById(expectedBudget.getId());
		assertThat(budgetRepository.findBudgetById(expectedBudget.getId())).isEmpty();
	}

	@Test
	void shouldGetAllBudgetsByUserId() {

		List<Budget> budgetList = budgetRepository.findBudgetsByUserId(user.getId());

		assertThat(budgetList).hasSize(2);
		assertThat(budgetList.get(0).getBudgetName()).isEqualTo(expectedBudget.getBudgetName());
		assertThat(budgetList.get(1).getBudgetName()).isEqualTo(expectedBudget2.getBudgetName());
	}

}
