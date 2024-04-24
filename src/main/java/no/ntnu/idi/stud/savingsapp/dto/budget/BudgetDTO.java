package no.ntnu.idi.stud.savingsapp.dto.budget;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
public class BudgetDTO {

  private Long id;
  private String budgetName;
  private BigDecimal budgetAmount;
  private BigDecimal expenseAmount;
  private BigDecimal balance;
  private Timestamp createdAt;
  private List<ExpenseDTO> expenses;
}
