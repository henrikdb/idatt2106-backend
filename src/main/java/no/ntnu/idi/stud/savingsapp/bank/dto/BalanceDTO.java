package no.ntnu.idi.stud.savingsapp.bank.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BalanceDTO {

  private Long bban;
  private BigDecimal balance;
}
