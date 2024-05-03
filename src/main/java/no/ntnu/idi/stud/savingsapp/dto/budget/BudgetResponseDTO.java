package no.ntnu.idi.stud.savingsapp.dto.budget;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Data;

@Data
public class BudgetResponseDTO {

	private Long id;

	private String budgetName;

	private BigDecimal budgetAmount;

	private BigDecimal expenseAmount;

	private Timestamp createdAt;

}
