package no.ntnu.idi.stud.savingsapp.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import no.ntnu.idi.stud.savingsapp.dto.auth.AuthenticationResponse;
import no.ntnu.idi.stud.savingsapp.dto.auth.BankIDRequest;
import no.ntnu.idi.stud.savingsapp.dto.auth.LoginRequest;
import no.ntnu.idi.stud.savingsapp.dto.auth.SignUpRequest;
import no.ntnu.idi.stud.savingsapp.exception.ExceptionResponse;
import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import no.ntnu.idi.stud.savingsapp.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling authentication related requests.
 */
@RestController
@RequestMapping("/api/auth")
@EnableAutoConfiguration
@Validated
@Tag(name = "Authentication")
public class AuthenticationController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Handles user login requests.
   *
   * @param request The login request.
   * @return ResponseEntity containing the authentication response.
   * @throws InvalidCredentialsException if the provided credentials are invalid.
   * @throws UserNotFoundException if the user is not found.
   */
  @Operation(summary = "User Login", description = "Log in with an existing user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully logged in"),
      @ApiResponse(responseCode = "401", description = "Invalid credentials",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @SecurityRequirements
  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) {
    User user = userService.login(request.getEmail(), request.getPassword());
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.ok(new AuthenticationResponse(user.getFirstName(),
        user.getLastName(), user.getId(), user.getProfileImage(), user.getRole().name(),
        user.getSubscriptionLevel().name(), token));
  }

  /**
   * Handles user signup requests.
   *
   * @param request The signup request.
   * @return ResponseEntity containing the authentication response.
   * @throws EmailAlreadyExistsException if the email is registered with an existing user.
   */
  @Operation(summary = "User Signup", description = "Sign up a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully signed up"),
      @ApiResponse(responseCode = "409", description = "Email already exists",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @SecurityRequirements
  @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<AuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
    User requestUser = modelMapper.map(request, User.class);
    User user = userService.register(requestUser);
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(user.getFirstName(),
        user.getLastName(), user.getId(), user.getProfileImage(), user.getRole().name(),
        user.getSubscriptionLevel().name(), token));
  }

  /**
   * Handles authentication requests using BankID.
   * This method processes an authentication request by taking a unique code and state from the BankID request,
   * verifies the user, and returns an authentication token along with user details.
   *
   * @param request The request body containing the authentication details required by BankID,
   *                specifically a 'code' and 'state' used for user verification.
   * @return A ResponseEntity object containing the user's authentication details including a JWT token
   *         if the authentication is successful.
   * @apiNote This method is protected by security requirements that must be met before the
   *          authentication can proceed.
   */
  @Operation(summary = "Authenticate a BankID request", description = "Authenticate a BankID request")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "If the authentication is successful")
  })
  @SecurityRequirements
  @PostMapping(value = "/bank-id")
  public ResponseEntity<AuthenticationResponse> bankIdAuthentication(@RequestBody BankIDRequest request) {
    User user = userService.bankIDAuth(request.getCode(), request.getState());
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthenticationResponse(user.getFirstName(),
        user.getLastName(), user.getId(), user.getProfileImage(), user.getRole().name(),
        user.getSubscriptionLevel().name(), token));
  }

  /**
   * Validates an email.
   *
   * @param email The email.
   * @return ResponseEntity.
   * @throws EmailAlreadyExistsException if the email is registered with an existing user.
   */
  @Operation(summary = "Validate email", description = "Check that the given email is valid")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Email is valid"),
      @ApiResponse(responseCode = "409", description = "Email already exists",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @SecurityRequirements
  @PostMapping(value = "/valid-email/{email}")
  public ResponseEntity<?> validateEmail(@PathVariable @Email(message = "Invalid email") String email) {
    try {
      userService.findByEmail(email);
      throw new EmailAlreadyExistsException();
    } catch (UserNotFoundException e) {
      return ResponseEntity.ok().build();
    }
  }
}
