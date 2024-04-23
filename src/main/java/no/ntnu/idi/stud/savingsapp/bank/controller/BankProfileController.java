package no.ntnu.idi.stud.savingsapp.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.service.BankProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling bank profile related operations.
 */
@RestController
@RequestMapping("/bank/v1/profile")
@EnableAutoConfiguration
public class BankProfileController {

  @Autowired
  private BankProfileService bankProfileService;

  @Operation(summary = "Create bank profile", description = "Create a bank profile by providing a"
      + " social security number")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully created a bank profile"),
      @ApiResponse(responseCode = "400", description = "Could not create profile")
  })
  @PostMapping("/create-profile")
  public BankProfileResponseDTO createBankProfile(@RequestBody BankProfileDTO bankProfileDTO) {
    return bankProfileService.saveBankProfile(bankProfileDTO);
  }

}
