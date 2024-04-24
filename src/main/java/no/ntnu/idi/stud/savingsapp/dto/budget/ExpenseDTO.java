package no.ntnu.idi.stud.savingsapp.dto.budget;

import lombok.Data;

@Data
public class ExpenseDTO {

  private Long id;
  private String description;
  private String amount;

}
