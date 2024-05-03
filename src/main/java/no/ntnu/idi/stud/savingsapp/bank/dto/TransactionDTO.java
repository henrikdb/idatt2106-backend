package no.ntnu.idi.stud.savingsapp.bank.dto;

import java.math.BigDecimal;
import lombok.Data;

/**
 * Represents a bank transaction.
 */
@Data
public class TransactionDTO {

  private Long debtorBBAN;
  private Long creditorBBAN;
  private BigDecimal amount;
}
