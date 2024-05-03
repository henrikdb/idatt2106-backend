package no.ntnu.idi.stud.savingsapp.bank.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Represents a response containing a bank account.
 */
@Data
public class AccountResponseDTO {

  private Long bankProfileId;
  private BigDecimal balance;

}
