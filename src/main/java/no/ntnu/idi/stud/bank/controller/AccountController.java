package no.ntnu.idi.stud.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import no.ntnu.idi.stud.bank.model.Account;
import no.ntnu.idi.stud.bank.service.AccountService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/bank/v2")
public class AccountController {

  private AccountService accountService;

  @Operation(summary = "Get user accounts", description = "Get accounts associated with a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got accounts"),
      @ApiResponse(responseCode = "200", description = "No accounts associated with a bank user"),
      @ApiResponse(responseCode = "404", description = "Bank profile id does not exist")
  })
  @GetMapping(value = "/accounts/{bankProfileId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Account>> getAccounts(@PathVariable Long bankProfileId) {
    List<Account> accounts;
    try {
      accounts = accountService.getAccountsByBankProfileId(bankProfileId);
    } catch (Exception e) {
      return (ResponseEntity) ResponseEntity.status(HttpStatusCode.valueOf(404));
    }
    return ResponseEntity.ok(accounts);
  }
}