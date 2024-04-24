package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;

import java.sql.Timestamp;
import java.util.List;

@Data
public final class CreateGoalDTO {

  @NonNull
  private String goalName;

  @NonNull
  private String description;

  @NonNull
  private int targetAmount;

  @NonNull
  private Timestamp targetDate;
}
