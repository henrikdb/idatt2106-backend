package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import lombok.NonNull;

@Data
public final class CreateGoalDTO {

  @NonNull
  private String goalName;

  @NonNull
  private String description;

  @NonNull
  private int targetAmount;

  @NonNull
  private String targetDate;
}
