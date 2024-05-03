package no.ntnu.idi.stud.savingsapp.dto.user;

import java.sql.Timestamp;
import lombok.Data;

@Data
public final class StreakDTO {

	private int currentStreak;

	private Timestamp currentStreakCreatedAt;

	private Timestamp currentStreakUpdatedAt;

	private int highestStreak;

	private Timestamp highestStreakCreatedAt;

	private Timestamp highestStreakEndedAt;

}
