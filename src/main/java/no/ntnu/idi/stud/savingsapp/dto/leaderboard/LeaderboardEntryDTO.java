package no.ntnu.idi.stud.savingsapp.dto.leaderboard;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.dto.dto.UserDTO;

@Data
public final class LeaderboardEntryDTO {

  private UserDTO user;
  private long value;

}
