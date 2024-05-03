package no.ntnu.idi.stud.savingsapp.bank.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AccountTest {

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser
	void shouldGetAccountsWithSsn() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/bank/v1/account/accounts/ssn/31125451740"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].bban").isNumber())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").isNumber());
	}

	@Test
	@WithMockUser
	void shouldNotGetAccountsWithWrongSsn() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/bank/v1/account/accounts/ssn/0"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	@WithMockUser
	void shouldGetAccountsWithBankProfileId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/bank/v1/account/accounts/profile/2"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].bban").isNumber())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").isNumber());
	}

	@Test
	@WithMockUser
	void shouldNotGetAccountsWithWrongBankProfileId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/bank/v1/account/accounts/profile/0"))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
