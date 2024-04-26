package no.ntnu.idi.stud.savingsapp.dto.budget;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ExpenseRequestDTO {

  private Long expenseId;
  private String description;
  private BigDecimal amount;

}
