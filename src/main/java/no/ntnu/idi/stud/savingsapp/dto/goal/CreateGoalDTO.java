package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import lombok.NonNull;

@Data
public final class CreateGoalDTO {

  @NonNull
  private String name;

  @NonNull
  private String description;

  private int targetAmount;

  @NonNull
  private String targetDate;
}
