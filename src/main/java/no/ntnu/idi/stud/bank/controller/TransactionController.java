package no.ntnu.idi.stud.bank.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.idi.stud.bank.dto.TransactionDTO;
import no.ntnu.idi.stud.bank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController("/bank/v2")
public class TransactionController {

  private TransactionService transactionService;

  @Operation(summary = "Transfer to account", description = "Transfer money from a users account "
      + "to another account of the same user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got accounts"),
      @ApiResponse(responseCode = "200", description = "No accounts associated with a bank user"),
      @ApiResponse(responseCode = "404", description = "Bank profile id does not exist")
  })
  @PostMapping("/norwegian-domestic-payment-to-self")
  public ResponseEntity<TransactionDTO> transferToSelf(
      @RequestBody TransactionDTO transactionRequest) {
    try {
       transactionService.saveTransaction(transactionRequest);
    } catch (ResponseStatusException e) {
      return (ResponseEntity) ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }
    return ResponseEntity.ok(transactionRequest);
  }
}
