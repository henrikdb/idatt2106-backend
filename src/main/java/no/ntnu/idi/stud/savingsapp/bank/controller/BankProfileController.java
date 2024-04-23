package no.ntnu.idi.stud.savingsapp.bank.controller;

import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.service.BankProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank/v1/profile")
@EnableAutoConfiguration
public class BankProfileController {

  @Autowired
  private BankProfileService bankProfileService;

  @PostMapping("/create-profile")
  public BankProfileResponseDTO createBankProfile(@RequestBody BankProfileDTO bankProfileDTO) {
    return bankProfileService.saveBankProfile(bankProfileDTO);
  }

}
