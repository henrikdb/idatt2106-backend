package no.ntnu.idi.stud.savingsapp.bank.dto;

import lombok.Data;

@Data
public class AccountResponseDTO {

  private Long bankProfileId;

  private Double balance;
}
