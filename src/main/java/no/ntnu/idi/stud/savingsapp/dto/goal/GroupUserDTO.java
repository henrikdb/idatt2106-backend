package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public final class GroupUserDTO {

  private Long userId;
  private BigDecimal amount;
}
