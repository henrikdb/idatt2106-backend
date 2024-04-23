package no.ntnu.idi.stud.savingsapp.bank.dto;

import java.util.List;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;

@Data
public class BankProfileResponseDTO {
  private Long ssn;
  List<Account> accounts;
}
