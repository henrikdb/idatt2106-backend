package no.ntnu.idi.stud.savingsapp.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import no.ntnu.idi.stud.savingsapp.dto.dto.UserUpdateDTO;
import no.ntnu.idi.stud.savingsapp.dto.dto.UserDTO;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller handling user related requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
@EnableAutoConfiguration
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ModelMapper modelMapper;

  @Operation(summary = "Update profile", description = "Update the profile of the authenticated user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated profile")
  })
  @PatchMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
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
    if (updateDTO.getPassword() != null) {
      String encodedPassword = passwordEncoder.encode(updateDTO.getPassword());
      user.setPassword(encodedPassword);
    }
    if (updateDTO.getCommitment() != null) {
      // TODO
    }
    if (updateDTO.getExperience() != null) {
      // TODO
    }
    if (updateDTO.getChallengeTypes() != null) {
      // TODO
    }
    User updatedUser = userService.update(user);
    UserDTO userDTO = modelMapper.map(updatedUser, UserDTO.class);
    return ResponseEntity.ok(userDTO);
  }
}
