package no.ntnu.idi.stud.savingsapp.model.leaderboard;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Leaderboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Leaderboard {

	private List<LeaderboardEntry> entries;

	private LeaderboardType type;

	private LeaderboardFilter filter;

}
