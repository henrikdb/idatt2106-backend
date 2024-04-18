package no.ntnu.idi.stud.savingsapp.controller;

import jakarta.validation.Valid;
import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.auth.AuthenticationResponse;
import no.ntnu.idi.stud.savingsapp.dto.auth.LoginRequest;
import no.ntnu.idi.stud.savingsapp.dto.auth.SignUpRequest;
import no.ntnu.idi.stud.savingsapp.exception.ExceptionResponse;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import no.ntnu.idi.stud.savingsapp.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling authentication related requests.
 */
@RestController
@RequestMapping("/api/auth")
@EnableAutoConfiguration
@Tag(name = "Authentication", description = "User authentication")
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
        user.getLastName(), user.getRole().name(), token));
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
  public ResponseEntity<AuthenticationResponse> signup(@RequestBody @Valid SignUpRequest request) {
    User requestUser = modelMapper.map(request, User.class);
    User user = userService.register(requestUser);
    String token = TokenUtils.generateToken(user);
    return ResponseEntity.ok(new AuthenticationResponse(user.getFirstName(),
        user.getLastName(), user.getRole().name(), token));
  }
}
