package no.ntnu.idi.stud.savingsapp.dto.user;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.BankAccountType;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;

@Data
public class BankAccountDTO {

	private Long bban;

	@Enumerator(value = BankAccountType.class)
	private String bankAccountType;

}
