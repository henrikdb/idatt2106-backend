package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;

import java.math.BigDecimal;

@Data
public final class MarkChallengeDTO {

  private long id;
  private int day;
  private BigDecimal amount;
}
