package no.ntnu.idi.stud.savingsapp.dto.budget;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BudgetRequestDTO {

	private String budgetName;

	private BigDecimal budgetAmount;

	private BigDecimal expenseAmount;

}
