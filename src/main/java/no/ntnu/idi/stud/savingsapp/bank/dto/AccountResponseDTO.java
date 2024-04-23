package no.ntnu.idi.stud.savingsapp.bank.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountResponseDTO {

  private Long bankProfileId;

  private BigDecimal balance;
}
