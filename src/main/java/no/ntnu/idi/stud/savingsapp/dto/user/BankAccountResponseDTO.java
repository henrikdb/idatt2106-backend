package no.ntnu.idi.stud.savingsapp.dto.user;

import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BankAccountResponseDTO {
  private Long bban;
  private BigDecimal balance;
}
