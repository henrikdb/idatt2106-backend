package no.ntnu.idi.stud.savingsapp.controller.leaderboard;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardEntry;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.service.LeaderboardService;
import no.ntnu.idi.stud.savingsapp.UserUtil;
import no.ntnu.idi.stud.savingsapp.controller.user.UserControllerTest;

@SpringBootTest
@AutoConfigureMockMvc
public class LeaderboardControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private LeaderboardService leaderboardService;

	private User user1;

	private User user2;

	private User user3;

	private Leaderboard leaderboard;

	private LeaderboardType leaderboardType;

	private LeaderboardFilter leaderboardFilter;

	private List<LeaderboardEntry> entries;

	@BeforeEach
	public void setup() {
		user1 = new User();
		user1.setId(1L);
		user1.setRole(Role.USER);
		user1.setEmail("test@example.com");
		user1.setFirstName("John");
		user1.setLastName("Doe");

		user2 = new User();
		user2.setId(2L);
		user2.setRole(Role.USER);
		user2.setEmail("another@example.com");
		user2.setFirstName("Jane");
		user2.setLastName("Smith");

		user3 = new User();
		user3.setId(3L);
		user3.setRole(Role.USER);
		user3.setEmail("someone@example.com");
		user3.setFirstName("Alice");
		user3.setLastName("Johnson");

		entries = new ArrayList<>();
		entries.add(new LeaderboardEntry(user1, 10, 1));
		entries.add(new LeaderboardEntry(user2, 5, 2));
		entries.add(new LeaderboardEntry(user3, 1, 3));

		leaderboard = new Leaderboard();
		leaderboard.setEntries(entries);
	}

	// GET TOP USERS GLOBAL
	@Test
	void getTopUsersCurrentStreakGlobal_Success() throws Exception {
		leaderboardType = LeaderboardType.CURRENT_STREAK;
		leaderboardFilter = LeaderboardFilter.GLOBAL;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getTopUsers(leaderboardType, leaderboardFilter, 3, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard").param("type", "CURRENT_STREAK")
			.param("filter", "GLOBAL")
			.param("entryCount", "3")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getTopUsersTopStreakGlobal_Success() throws Exception {
		leaderboardType = LeaderboardType.TOP_STREAK;
		leaderboardFilter = LeaderboardFilter.GLOBAL;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getTopUsers(leaderboardType, leaderboardFilter, 3, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard").param("type", "TOP_STREAK")
			.param("filter", "GLOBAL")
			.param("entryCount", "3")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getTopUsersTotalPointsGlobal_Success() throws Exception {
		leaderboardType = LeaderboardType.TOTAL_POINTS;
		leaderboardFilter = LeaderboardFilter.GLOBAL;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getTopUsers(leaderboardType, leaderboardFilter, 3, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard").param("type", "TOTAL_POINTS")
			.param("filter", "GLOBAL")
			.param("entryCount", "3")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	// GET TOP USERS FRIENDS
	@Test
	void getTopUsersCurrentStreakFriends_Success() throws Exception {
		leaderboardType = LeaderboardType.CURRENT_STREAK;
		leaderboardFilter = LeaderboardFilter.FRIENDS;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getTopUsers(leaderboardType, leaderboardFilter, 3, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard").param("type", "CURRENT_STREAK")
			.param("filter", "FRIENDS")
			.param("entryCount", "3")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getTopUsersTopStreakFriends_Success() throws Exception {
		leaderboardType = LeaderboardType.TOP_STREAK;
		leaderboardFilter = LeaderboardFilter.FRIENDS;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getTopUsers(leaderboardType, leaderboardFilter, 3, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard").param("type", "TOP_STREAK")
			.param("filter", "FRIENDS")
			.param("entryCount", "3")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getTopUsersTotalPointsFriends_Success() throws Exception {
		leaderboardType = LeaderboardType.TOTAL_POINTS;
		leaderboardFilter = LeaderboardFilter.FRIENDS;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getTopUsers(leaderboardType, leaderboardFilter, 3, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard").param("type", "TOTAL_POINTS")
			.param("filter", "FRIENDS")
			.param("entryCount", "3")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	// GET SURROUNDING GLOBAL
	@Test
	void getSurroundingCurrentStreakGlobal_Success() throws Exception {
		leaderboardType = LeaderboardType.CURRENT_STREAK;
		leaderboardFilter = LeaderboardFilter.GLOBAL;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getSurrounding(leaderboardType, leaderboardFilter, 2, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard/surrounding").param("type", "CURRENT_STREAK")
			.param("filter", "GLOBAL")
			.param("entryCount", "2")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getSurroundingTopStreakGlobal_Success() throws Exception {
		leaderboardType = LeaderboardType.TOP_STREAK;
		leaderboardFilter = LeaderboardFilter.GLOBAL;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getSurrounding(leaderboardType, leaderboardFilter, 2, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard/surrounding").param("type", "TOP_STREAK")
			.param("filter", "GLOBAL")
			.param("entryCount", "2")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getSurroundingTotalPointsGlobal_Success() throws Exception {
		leaderboardType = LeaderboardType.TOTAL_POINTS;
		leaderboardFilter = LeaderboardFilter.GLOBAL;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getSurrounding(leaderboardType, leaderboardFilter, 2, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard/surrounding").param("type", "TOTAL_POINTS")
			.param("filter", "GLOBAL")
			.param("entryCount", "2")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	// GET SURROUNDING FRIENDS
	@Test
	void getSurroundingCurrentStreakFriends_Success() throws Exception {
		leaderboardType = LeaderboardType.CURRENT_STREAK;
		leaderboardFilter = LeaderboardFilter.FRIENDS;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getSurrounding(leaderboardType, leaderboardFilter, 2, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard/surrounding").param("type", "CURRENT_STREAK")
			.param("filter", "FRIENDS")
			.param("entryCount", "2")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getSurroundingTopStreakFriends_Success() throws Exception {
		leaderboardType = LeaderboardType.TOP_STREAK;
		leaderboardFilter = LeaderboardFilter.FRIENDS;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getSurrounding(leaderboardType, leaderboardFilter, 2, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard/surrounding").param("type", "TOP_STREAK")
			.param("filter", "FRIENDS")
			.param("entryCount", "2")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

	@Test
	void getSurroundingTotalPointsFriends_Success() throws Exception {
		leaderboardType = LeaderboardType.TOTAL_POINTS;
		leaderboardFilter = LeaderboardFilter.FRIENDS;
		leaderboard.setType(leaderboardType);
		leaderboard.setFilter(leaderboardFilter);

		when(leaderboardService.getSurrounding(leaderboardType, leaderboardFilter, 2, 1L)).thenReturn(leaderboard);

		mvc.perform(get("/api/leaderboard/surrounding").param("type", "TOTAL_POINTS")
			.param("filter", "FRIENDS")
			.param("entryCount", "2")
			.with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user1))))
			.andExpect(status().isOk());
	}

}
