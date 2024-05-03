package no.ntnu.idi.stud.savingsapp.dto.leaderboard;

import lombok.Data;

import java.util.List;

@Data
public final class LeaderboardDTO {

	private String type;

	private String filter;

	private List<LeaderboardEntryDTO> entries;

}
