package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;
import no.ntnu.idi.stud.savingsapp.service.impl.LeaderboardServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class LeaderboardServiceTest {

  @Autowired
  private LeaderboardServiceImpl leaderboardService;

  // Top leaderboard
  @Test
  void shouldGetTopGlobalUsersFiltredByTotalPoints() {
    Leaderboard leaderboard = leaderboardService.getTopUsers(LeaderboardType.TOTAL_POINTS,
        LeaderboardFilter.GLOBAL, 3,
        1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOTAL_POINTS);
    assertThat(leaderboard.getEntries().get(0).getScore()).isEqualTo(1050);
  }

  @Test
  void shouldGetTopGlobalUsersFiltredByCurrentStreak() {
    Leaderboard leaderboard = leaderboardService.getTopUsers(LeaderboardType.CURRENT_STREAK,
        LeaderboardFilter.GLOBAL, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.CURRENT_STREAK);
    assertThat(leaderboard.getEntries().get(0).getScore()).isEqualTo(12);
  }

  @Test
  void shouldGetTopGlobalUsersFiltredByTopStreak() {
    Leaderboard leaderboard = leaderboardService.getTopUsers(LeaderboardType.TOP_STREAK,
        LeaderboardFilter.GLOBAL, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOP_STREAK);
    assertThat(leaderboard.getEntries().get(0).getScore()).isEqualTo(20);
  }

  @Test
  void shouldGetTopFriendsUsersFiltredByTotalPoints() {
    Leaderboard leaderboard = leaderboardService.getTopUsers(LeaderboardType.TOTAL_POINTS,
        LeaderboardFilter.FRIENDS, 3,
        1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOTAL_POINTS);
    assertThat(leaderboard.getEntries().get(0).getScore()).isEqualTo(1000);
  }

  @Test
  void shouldGetTopFriendsUsersFiltredByCurrentStreak() {
    Leaderboard leaderboard = leaderboardService.getTopUsers(LeaderboardType.CURRENT_STREAK,
        LeaderboardFilter.FRIENDS, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(2);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.CURRENT_STREAK);
    assertThat(leaderboard.getEntries().get(0).getScore()).isEqualTo(5);
  }

  @Test
  void shouldGetTopFriendsUsersFiltredByTopStreak() {
    Leaderboard leaderboard = leaderboardService.getTopUsers(LeaderboardType.TOP_STREAK,
        LeaderboardFilter.FRIENDS, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOP_STREAK);
    assertThat(leaderboard.getEntries().get(0).getScore()).isEqualTo(15);
  }

  // Surrounding leaderboard
  @Test
  void shouldGetSurroundingGlobalUsersFiltredByTotalPoints() {
    Leaderboard leaderboard = leaderboardService.getSurrounding(LeaderboardType.TOTAL_POINTS,
        LeaderboardFilter.GLOBAL, 3,
        1L);

    assertThat(leaderboard.getEntries()).hasSize(5);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOTAL_POINTS);
  }

  @Test
  void shouldGetSurroundingGlobalUsersFiltredByCurrentStreak() {
    Leaderboard leaderboard = leaderboardService.getSurrounding(LeaderboardType.CURRENT_STREAK,
        LeaderboardFilter.GLOBAL, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(8);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.CURRENT_STREAK);
  }

  @Test
  void shouldGetSurroundingGlobalUsersFiltredByTopStreak() {
    Leaderboard leaderboard = leaderboardService.getSurrounding(LeaderboardType.TOP_STREAK,
        LeaderboardFilter.GLOBAL, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(5);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOP_STREAK);
  }

  @Test
  void shouldGetSurroundingFriendsUsersFiltredByTotalPoints() {
    Leaderboard leaderboard = leaderboardService.getSurrounding(LeaderboardType.TOTAL_POINTS,
        LeaderboardFilter.FRIENDS, 3,
        1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOTAL_POINTS);
  }

  @Test
  void shouldGetSurroundingFriendsUsersFiltredByCurrentStreak() {
    Leaderboard leaderboard = leaderboardService.getSurrounding(LeaderboardType.CURRENT_STREAK,
        LeaderboardFilter.FRIENDS, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.CURRENT_STREAK);
  }

  @Test
  void shouldGetSurroundingFriendsUsersFiltredByTopStreak() {
    Leaderboard leaderboard = leaderboardService.getSurrounding(LeaderboardType.TOP_STREAK,
        LeaderboardFilter.FRIENDS, 3, 1L);

    assertThat(leaderboard.getEntries()).hasSize(3);
    assertThat(leaderboard.getType()).isEqualTo(LeaderboardType.TOP_STREAK);
  }

}
