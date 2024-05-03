package no.ntnu.idi.stud.savingsapp.dto.leaderboard;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;

@Data
public final class LeaderboardEntryDTO {

	private UserDTO user;

	private int score;

	private long rank;

}
