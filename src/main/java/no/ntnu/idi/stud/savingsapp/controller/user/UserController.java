package no.ntnu.idi.stud.savingsapp.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.dto.user.*;
import no.ntnu.idi.stud.savingsapp.exception.user.PermissionDeniedException;
import no.ntnu.idi.stud.savingsapp.model.BankAccountType;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.configuration.Commitment;
import no.ntnu.idi.stud.savingsapp.model.configuration.Experience;
import no.ntnu.idi.stud.savingsapp.model.user.Feedback;
import no.ntnu.idi.stud.savingsapp.model.user.SearchFilter;
import no.ntnu.idi.stud.savingsapp.model.user.SubscriptionLevel;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;
/**
 * Controller handling user related requests.
 */
@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/users")
@EnableAutoConfiguration
@Tag(name = "User")
@Slf4j
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
    log.info("[UserController:getUser] user: {}", userDTO);
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
    log.info("[UserController:getProfile] profile: {}", profileDTO);
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
    if(updateDTO.getBannerImage() != null) {
      user.setBannerImage(updateDTO.getBannerImage());
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
    log.info("[UserController:update] updated user id: {}", identity.getId());
    return ResponseEntity.ok(userDTO);
  }

  @Operation(summary = "Update a password", description = "Update the password of the authenticated user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated password")
  })
  @PatchMapping(value = "/password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> updatePassword(@AuthenticationPrincipal AuthIdentity identity,
                                        @RequestBody @Valid PasswordUpdateDTO updateDTO) {
    User user = userService.updatePassword(identity.getId(), updateDTO.getOldPassword(), updateDTO.getNewPassword());
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    log.info("[UserController:updatedPassword] user id: {}", identity.getId());
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
    log.info("[UserController:resetPassword] initiated password reset, email: {}", email);
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
    log.info("[UserController:confirmPasswordReset] initiated password reset, token: {}", resetDTO.getToken());
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
    log.info("[UserController:selectBankAccount], bankAccount: {}", bankAccountDTO);
    return userService.selectBankAccount(
        accountType,
        bankAccountDTO.getBban(),
        identity.getId());
  }

  @Operation(summary = "Search for users by name and filter", description = "Returns a list of users whose names contain the specified search term and match the filter.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
  })
  @GetMapping("/search/{searchTerm}/{filter}")
  public ResponseEntity<List<UserDTO>> getUsersByNameAndFilter(
          @AuthenticationPrincipal AuthIdentity identity,
          @PathVariable String searchTerm,
          @PathVariable @Enumerator(value = SearchFilter.class,
          message = "Invalid filter") String filter) {
          List<User> users = userService.getUsersByNameAndFilter(identity.getId(), searchTerm, SearchFilter.valueOf(filter));
          List<UserDTO> userDTOs = new ArrayList<>();
          for(User user : users) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOs.add(userDTO);
          }
          log.info("[UserController:getRandomUsers] random users: {}", userDTOs);
          return ResponseEntity.ok(userDTOs);
  }

  /**
   * Sends feedback from an email.
   */
  @Operation(summary = "Send feedback", description = "Send feedback from an email.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success")
  })
  @PostMapping("/send-feedback")
  public ResponseEntity<Void> sendFeedback(@Validated @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
    userService.sendFeedback(feedbackRequestDTO.getEmail(), feedbackRequestDTO.getMessage());
    log.info("[UserController:sendFeedback] feedback: {}", feedbackRequestDTO);
    return ResponseEntity.ok().build();
  }

  /**
   * Get all feedback.
   *
   * @param identity The authenticated user's identity.
   * @return A list containing all feedback.
   */
  @Operation(summary = "Send feedback", description = "Send feedback from a user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success")
  })
  @GetMapping("/get-feedback")
  public ResponseEntity<List<FeedbackResponseDTO>> getFeedback(@AuthenticationPrincipal AuthIdentity identity) {
    if (!identity.getRole().equalsIgnoreCase("ADMIN")) {
      log.error("[UserController:getFeedback] Permission denied, user role: {}", identity.getRole());
      throw new PermissionDeniedException();
    }
    List<Feedback> feedbacks = userService.getFeedback();
    List<FeedbackResponseDTO> feedbackResponseDTOS = feedbacks.stream().map(quiz -> modelMapper.map(quiz, FeedbackResponseDTO.class)).toList();
    log.info("[UserController:getFeedback] feedback: {}", feedbackResponseDTOS);
    return ResponseEntity.ok(feedbackResponseDTOS);
  }
  
  @Operation(summary = "Get X amount of random users", description = "Get X amount of random users that fit the filter")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
  })
  @GetMapping("/search/random/{amount}/{filter}")
  public ResponseEntity<List<UserDTO>> getRandomUsers(
          @AuthenticationPrincipal AuthIdentity identity,
          @PathVariable int amount, 
          @PathVariable @Enumerator(value = SearchFilter.class,
          message = "Invalid filter") String filter) {
          List<User> users = userService.getRandomUsers(identity.getId(), amount, SearchFilter.valueOf(filter));
          List<UserDTO> userDTOs = new ArrayList<>();
          for(User user : users) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOs.add(userDTO);
          }
          log.info("[UserController:getUsersByNameAndFilter] users: {}", userDTOs);
          return ResponseEntity.ok(userDTOs);
  }

  @Operation(summary = "Update User Subscription Level", description = "Updates the subscription level of the current user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Subscription level updated successfully")
  })
  @PutMapping("/subscription/{subscriptionLevel}")
  public ResponseEntity<?> updateSubscriptionLevel(
          @AuthenticationPrincipal AuthIdentity identity,
          @PathVariable @Enumerator(value = SubscriptionLevel.class, message = "Invalid subscription level") String subscriptionLevel) {
            userService.updateSubscriptionLevel(identity.getId(), SubscriptionLevel.valueOf(subscriptionLevel));
            log.info("[UserController:updateSubscriptionLevel] subscription level: {}", subscriptionLevel);
            return ResponseEntity.ok().build();
  }
}


