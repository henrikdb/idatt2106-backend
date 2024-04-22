package no.ntnu.idi.stud.bank.dto;

import lombok.Data;

@Data
public class TransactionDTO {

  private Long debtorBBAN;

  private Long creditorBBAN;

  private Double amount;
}
