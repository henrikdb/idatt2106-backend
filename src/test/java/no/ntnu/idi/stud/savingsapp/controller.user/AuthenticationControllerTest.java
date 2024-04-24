package no.ntnu.idi.stud.savingsapp.controller.user;

import no.ntnu.idi.stud.savingsapp.JsonUtil;
import no.ntnu.idi.stud.savingsapp.dto.auth.LoginRequest;
import no.ntnu.idi.stud.savingsapp.dto.auth.SignUpRequest;
import no.ntnu.idi.stud.savingsapp.dto.configuration.ConfigurationDTO;
import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  private User user;

  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
    user.setPassword("securePassword1");
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setRole(Role.USER);
  }

  @Test
  void login_Success() throws Exception {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(user.getEmail());
    loginRequest.setPassword(user.getPassword());

    when(userService.login(user.getEmail(), user.getPassword())).thenReturn(user);

    mvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(loginRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  void login_InvalidCredentials() throws Exception {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(user.getEmail());
    loginRequest.setPassword("wrongPassword1");

    when(userService.login(user.getEmail(), "wrongPassword1"))
        .thenThrow(new InvalidCredentialsException());

    mvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(loginRequest)))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void signup_Success() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail(user.getEmail());
    signUpRequest.setPassword(user.getPassword());
    signUpRequest.setFirstName(user.getFirstName());
    signUpRequest.setLastName(user.getLastName());
    ConfigurationDTO configurationDTO = new ConfigurationDTO();
    configurationDTO.setCommitment("MUCH");
    configurationDTO.setExperience("EXPERT");
    configurationDTO.setChallengeTypes(Arrays.asList("NO_COFFEE"));
    signUpRequest.setConfiguration(configurationDTO);

    when(userService.register(any())).thenReturn(user);

    mvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(signUpRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").exists());
  }

  @Test
  void signup_EmailExists() throws Exception {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail(user.getEmail());
    signUpRequest.setPassword(user.getPassword());
    signUpRequest.setFirstName(user.getFirstName());
    signUpRequest.setLastName(user.getLastName());
    ConfigurationDTO configurationDTO = new ConfigurationDTO();
    configurationDTO.setCommitment("MUCH");
    configurationDTO.setExperience("EXPERT");
    configurationDTO.setChallengeTypes(Arrays.asList("NO_COFFEE"));
    signUpRequest.setConfiguration(configurationDTO);

    when(userService.register(any())).thenThrow(new EmailAlreadyExistsException());

    mvc.perform(post("/api/auth/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(signUpRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  void validateEmail_EmailExists() throws Exception {
    String email = "existing@example.com";
    when(userService.findByEmail(email)).thenThrow(new EmailAlreadyExistsException());

    mvc.perform(post("/api/auth/valid-email/{email}", email))
        .andExpect(status().isConflict());
  }

  @Test
  void validateEmail_EmailValid() throws Exception {
    String email = "new@example.com";
    when(userService.findByEmail(email)).thenThrow(new UserNotFoundException());

    mvc.perform(post("/api/auth/valid-email/{email}", email))
        .andExpect(status().isOk());
  }
}
