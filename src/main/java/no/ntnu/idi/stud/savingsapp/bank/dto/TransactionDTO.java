package no.ntnu.idi.stud.savingsapp.bank.dto;

import lombok.Data;

@Data
public class TransactionDTO {

  private Long debtorBBAN;

  private Long creditorBBAN;

  private Double amount;
}
