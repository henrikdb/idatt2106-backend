package no.ntnu.idi.stud.savingsapp.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountRequestDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.AccountResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling bank account related operations.
 */
@RestController
@RequestMapping("/bank/v1/account")
@EnableAutoConfiguration
@Slf4j
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Operation(summary = "Get user accounts", description = "Get accounts associated with a user by"
      + " providing their bank profile id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got accounts"),
      @ApiResponse(responseCode = "200", description = "No accounts associated with a bank user"),
      @ApiResponse(responseCode = "404", description = "Bank profile id does not exist")
  })
  @GetMapping("/accounts/profile/{bankProfileId}")
  public ResponseEntity<List<Account>> getAccounts(@PathVariable Long bankProfileId) {
    List<Account> accounts = accountService.getAccountsByBankProfileId(bankProfileId);
    log.info("[AccountController:getAccounts] bankProfileId: {}", bankProfileId);
    for (Account account : accounts) {
      log.info("[AccountController:getAccounts] accountBban: {}", account.getBban());
    }
    return ResponseEntity.ok(accounts);
  }

  @Operation(summary = "Get user accounts", description = "Get accounts associated with a user by"
      + " providing their social security number")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got accounts"),
      @ApiResponse(responseCode = "200", description = "No accounts associated with a bank user"),
      @ApiResponse(responseCode = "404", description = "Social security number does not exist")
  })
  @GetMapping("/accounts/ssn/{ssn}")
  public ResponseEntity<List<Account>> getAccountsBySsn(@PathVariable Long ssn) {
    List<Account> accounts = accountService.getAccountsBySsn(ssn);
    log.info("[AccountController:getAccountsBySsn] ssn: {}", ssn);
    for (Account account : accounts) {
      log.info("[AccountController:getAccountsBySsn] accountBban: {}", account.getBban());
    }
    return ResponseEntity.ok(accounts);
  }

  @Operation(summary = "Create account", description = "Create account with random balance")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully created account"),
      @ApiResponse(responseCode = "404", description = "Provided bank profile id could not be "
          + "found")
  })
  @PostMapping("/create-account")
  public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
    AccountResponseDTO accountResponseDTO = accountService.saveAccount(accountRequestDTO);
    log.info("[AccountController:createAccount] accountBankProfileId: {}", accountResponseDTO.getBankProfileId());
    return ResponseEntity.ok(accountResponseDTO);
  }
}