package no.ntnu.idi.stud.savingsapp.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an authentication response containing a token.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {

  private String firstName;
  private String lastName;
  private String role;
  private String subscriptionLevel;
  private String token;
}
