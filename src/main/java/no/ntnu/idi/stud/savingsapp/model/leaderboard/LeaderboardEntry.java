package no.ntnu.idi.stud.savingsapp.model.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Represents an entry in a leaderboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardEntry {
    private User user;
    private int score;
    private long rank;
}
