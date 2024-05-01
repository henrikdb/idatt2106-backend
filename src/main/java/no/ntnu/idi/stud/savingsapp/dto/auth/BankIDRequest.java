package no.ntnu.idi.stud.savingsapp.dto.auth;

import lombok.Data;

@Data
public final class BankIDRequest {

  private String code;
  private String state;

}
