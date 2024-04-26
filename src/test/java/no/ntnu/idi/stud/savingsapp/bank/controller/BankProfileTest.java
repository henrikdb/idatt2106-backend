package no.ntnu.idi.stud.savingsapp.bank.controller;

import no.ntnu.idi.stud.savingsapp.JsonUtil;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
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
public class BankProfileTest {

  @Autowired
  private MockMvc mvc;


  @Test
  @WithMockUser
  void shouldRegisterNewBankProfile() throws Exception{
    BankProfileDTO bankProfileDTO = new BankProfileDTO();
    bankProfileDTO.setSsn(31125452887L);
    mvc.perform(MockMvcRequestBuilders.post("/bank/v1/profile/create-profile")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(bankProfileDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.ssn").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.accounts").exists());
  }
}
