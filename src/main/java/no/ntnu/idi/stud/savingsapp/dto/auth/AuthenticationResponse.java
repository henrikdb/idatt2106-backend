package no.ntnu.idi.stud.savingsapp.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an authentication response containing a token.
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse {

  private String token;
}
