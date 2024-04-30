package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;

import java.math.BigDecimal;

@Data
public final class ChallengeUpdateStateDTO {

  private long challengeId;
  private int challengeDay;
  private BigDecimal amount;
}
