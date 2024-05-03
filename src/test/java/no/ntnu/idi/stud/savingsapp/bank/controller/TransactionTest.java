package no.ntnu.idi.stud.savingsapp.bank.controller;

import java.math.BigDecimal;
import no.ntnu.idi.stud.savingsapp.JsonUtil;
import no.ntnu.idi.stud.savingsapp.bank.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TransactionTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser
	void shouldTransferMoneyBetweenAccounts() throws Exception {
		TransactionDTO transactionRequestDTO = new TransactionDTO();
		transactionRequestDTO.setAmount(BigDecimal.valueOf(50));
		transactionRequestDTO.setDebtorBBAN(12073650567L);
		transactionRequestDTO.setCreditorBBAN(12097256355L);
		mvc.perform(MockMvcRequestBuilders.post("/bank/v1/transaction/norwegian-domestic-payment-to-self")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(transactionRequestDTO)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.amount").isNumber())
			.andExpect(MockMvcResultMatchers.jsonPath("$.debtorBBAN").isNumber())
			.andExpect(MockMvcResultMatchers.jsonPath("$.creditorBBAN").isNumber());
	}

	@Test
	@WithMockUser
	void shouldNotTransferMoneyBetweenWrongAccounts() throws Exception {
		TransactionDTO transactionRequestDTO = new TransactionDTO();
		transactionRequestDTO.setAmount(BigDecimal.valueOf(50));
		transactionRequestDTO.setDebtorBBAN(0L);
		transactionRequestDTO.setCreditorBBAN(12097256355L);
		mvc.perform(MockMvcRequestBuilders.post("/bank/v1/transaction/norwegian-domestic-payment-to-self")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(transactionRequestDTO))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser
	void shouldNotTransferMoneyWithNegativeTransferAmount() throws Exception {
		TransactionDTO transactionRequestDTO = new TransactionDTO();
		transactionRequestDTO.setAmount(BigDecimal.valueOf(-50));
		transactionRequestDTO.setDebtorBBAN(12073650567L);
		transactionRequestDTO.setCreditorBBAN(12097256355L);
		mvc.perform(MockMvcRequestBuilders.post("/bank/v1/transaction/norwegian-domestic-payment-to-self")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(transactionRequestDTO))).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	@WithMockUser
	void shouldNotTransferMoneyWithInsufficientFunds() throws Exception {
		TransactionDTO transactionRequestDTO = new TransactionDTO();
		transactionRequestDTO.setAmount(BigDecimal.valueOf(10000000));
		transactionRequestDTO.setDebtorBBAN(12073650567L);
		transactionRequestDTO.setCreditorBBAN(12097256355L);
		mvc.perform(MockMvcRequestBuilders.post("/bank/v1/transaction/norwegian-domestic-payment-to-self")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(transactionRequestDTO)))
			.andExpect(MockMvcResultMatchers.status().isPaymentRequired());
	}

}
