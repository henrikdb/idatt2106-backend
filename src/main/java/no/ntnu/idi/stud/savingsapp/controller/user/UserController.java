package no.ntnu.idi.stud.savingsapp.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.dto.user.BankAccountDTO;
import no.ntnu.idi.stud.savingsapp.dto.user.PasswordResetDTO;
import no.ntnu.idi.stud.savingsapp.dto.user.ProfileDTO;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;
import no.ntnu.idi.stud.savingsapp.dto.user.UserUpdateDTO;
import no.ntnu.idi.stud.savingsapp.model.BankAccountType;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.configuration.Commitment;
import no.ntnu.idi.stud.savingsapp.model.configuration.Experience;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling user related requests.
 */
@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/users")
@EnableAutoConfiguration
@Tag(name = "User")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Retrieves the authenticated user's data.
   *
   * @param identity The security context of the authenticated user.
   * @return ResponseEntity containing the UserDTO of the authenticated user.
   * @apiNote This endpoint is used to fetch all user information for the authenticated user.
   *          It uses the user's ID stored in the authentication principal to fetch the data.
   */
  @Operation(summary = "Get the authenticated user", description = "Get all user information for " +
      "the authenticated user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got user")
  })
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal AuthIdentity identity) {
    User user = userService.findById(identity.getId());
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }

  /**
   * Retrieves the profile of a specific user by their unique identifier.
   *
   * @param userId The unique identifier of the user whose profile is to be retrieved.
   * @return ResponseEntity containing the ProfileDTO of the requested user.
   * @apiNote This endpoint fetches the profile of any user given their user ID.
   *          It is intended for public access where any authenticated user can view others' profiles.
   */
  @Operation(summary = "Get a profile", description = "Get the profile of a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got profile")
  })
  @GetMapping(value = "/{userId}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProfileDTO> getProfile(@PathVariable long userId) {
    User user = userService.findById(userId);
    ProfileDTO profileDTO = modelMapper.map(user, ProfileDTO.class);
    return ResponseEntity.ok(profileDTO);
  }

  /**
   * Updates the profile of the authenticated user based on the provided data.
   *
   * @param identity The security context of the authenticated user.
   * @param updateDTO The data transfer object containing the fields that need to be updated.
   * @return ResponseEntity containing the updated UserDTO.
   * @apiNote This endpoint allows the authenticated user to update their own profile.
   *          It only updates fields that are provided in the request body.
   */
  @Operation(summary = "Update a profile", description = "Update the profile of the authenticated user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated profile")
  })
  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> update(@AuthenticationPrincipal AuthIdentity identity,
                                        @RequestBody @Valid UserUpdateDTO updateDTO) {
    User user = userService.findById(identity.getId());
    if (updateDTO.getFirstName() != null) {
      user.setFirstName(updateDTO.getFirstName());
    }
    if (updateDTO.getLastName() != null) {
      user.setLastName(updateDTO.getLastName());
    }
    if (updateDTO.getEmail() != null) {
      user.setEmail(updateDTO.getEmail());
    }
    if (updateDTO.getEmail() != null) {
      user.setEmail(updateDTO.getEmail());
    }
    if (updateDTO.getProfileImage() != null) {
      user.setProfileImage(updateDTO.getProfileImage());
    }
    if (updateDTO.getConfiguration() != null) {
      if (updateDTO.getConfiguration().getCommitment() != null) {
        user.getConfiguration().setCommitment(Commitment.valueOf(updateDTO.getConfiguration().getCommitment()));
      }
      if (updateDTO.getConfiguration().getExperience() != null) {
        user.getConfiguration().setExperience(Experience.valueOf(updateDTO.getConfiguration().getExperience()));
      }
      if (updateDTO.getConfiguration().getChallengeTypes() != null) {
        for (String challengeType : updateDTO.getConfiguration().getChallengeTypes()) {
          user.getConfiguration().getChallengeTypes().add(ChallengeType.valueOf(challengeType));
        }
      }
    }
    User updatedUser = userService.update(user);
    UserDTO userDTO = modelMapper.map(updatedUser, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }

  /**
   * Initiates a password reset process by sending a reset email to the user with the provided email.
   * This endpoint is called when a user requests a password reset. It triggers an email with reset instructions.
   *
   * @param email The email address of the user requesting a password reset, which must be a valid email format.
   * @throws IllegalArgumentException if the email address does not meet the email format validation.
   */
  @Operation(summary = "Initiate a password reset", description = "Send a password reset mail " +
      "to the user with the specified email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "Successfully initiated a password reset")
  })
  @SecurityRequirements
  @PostMapping(value = "/reset-password", consumes = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(value = HttpStatus.ACCEPTED)
  public void resetPassword(@RequestBody @Email(message = "Invalid email") String email) {
    userService.initiatePasswordReset(email);
  }

  /**
   * Confirms a password reset using a provided token and a new password.
   * This endpoint is called to finalize the password reset process. It uses the token sent to the user's email
   * and a new password specified by the user to complete the password reset.
   *
   * @param resetDTO The PasswordResetDTO containing the reset token and the new password which must be valid.
   * @throws jakarta.validation.ValidationException if the resetDTO does not pass validation checks.
   */
  @Operation(summary = "Confirm a password reset", description = "Confirms a password reset " +
      "using a token and a new password")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Password was reset successfully"),
      @ApiResponse(responseCode = "403", description = "Invalid token")
  })
  @SecurityRequirements
  @PostMapping(value = "/confirm-password")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void confirmPasswordReset(@RequestBody @Valid PasswordResetDTO resetDTO) {
    userService.confirmPasswordReset(resetDTO.getToken(), resetDTO.getPassword());
  }

  @Operation(summary = "Update a user's bank account", description = "Changes either a user's "
      + "checking account or savings account")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "")
  })
  @PatchMapping(value = "/update-account")
  public Account selectBankAccount(
      @AuthenticationPrincipal AuthIdentity identity,
      @RequestBody @Valid BankAccountDTO bankAccountDTO) {
    BankAccountType accountType = modelMapper.map(bankAccountDTO.getBankAccountType(),
        BankAccountType.class);
    return userService.selectBankAccount(
        accountType,
        bankAccountDTO.getBban(),
        identity.getId());
  }
}
