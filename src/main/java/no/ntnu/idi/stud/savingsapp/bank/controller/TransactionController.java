package no.ntnu.idi.stud.savingsapp.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.bank.dto.TransactionDTO;
import no.ntnu.idi.stud.savingsapp.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling transaction related operations.
 */
@RestController
@RequestMapping("/bank/v1/transaction")
@EnableAutoConfiguration
@Slf4j
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @Operation(summary = "Transfer to account", description = "Transfer money from a users account "
      + "to another account of the same user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got accounts"),
      @ApiResponse(responseCode = "200", description = "No accounts associated with a bank user"),
      @ApiResponse(responseCode = "404", description = "Bank profile id does not exist")
  })
  @PostMapping("/norwegian-domestic-payment-to-self")
  public ResponseEntity<TransactionDTO> transferToSelf(@RequestBody TransactionDTO transactionRequest) {
    transactionService.saveTransaction(transactionRequest);
    log.info("[TransactionController:transferToSelf] transaction: {}", transactionRequest);
    return ResponseEntity.ok(transactionRequest);
  }
}
