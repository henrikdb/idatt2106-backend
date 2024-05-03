package no.ntnu.idi.stud.savingsapp.dto.budget;

import lombok.Data;

@Data
public class ExpenseResponseDTO {

	private Long expenseId;

	private Long budgetId;

	private String description;

	private String amount;

}
