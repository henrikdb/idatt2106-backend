package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public final class GoalDTO {

  private long id;
  private String name;
  private String description;
  private int targetAmount;
  private Timestamp targetDate;
  private Timestamp createdAt;
  private List<ChallengeDTO> challenges;
  private UserDTO user;
}
