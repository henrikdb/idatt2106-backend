package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public final class ProgressDTO {

  private long id;
  private int day;
  private BigDecimal amount;
  private Timestamp completedAt;

}
