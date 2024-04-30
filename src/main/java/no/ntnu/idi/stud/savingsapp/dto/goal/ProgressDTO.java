package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;

import java.sql.Timestamp;

@Data
public final class ProgressDTO {

  private Long id;

  private int challengeDay;

  private Timestamp completedAt;
}
