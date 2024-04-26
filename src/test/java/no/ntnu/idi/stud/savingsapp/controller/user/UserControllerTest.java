package no.ntnu.idi.stud.savingsapp.controller.user;

import no.ntnu.idi.stud.savingsapp.JsonUtil;
import no.ntnu.idi.stud.savingsapp.dto.user.PasswordResetDTO;
import no.ntnu.idi.stud.savingsapp.dto.user.UserUpdateDTO;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  private User user;

  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(1L);
    user.setRole(Role.USER);
    user.setEmail("test@example.com");
    user.setFirstName("John");
    user.setLastName("Doe");
  }

  @Test
  void getUser_Success() throws Exception {
    when(userService.findById(user.getId())).thenReturn(user);

    mvc.perform(get("/api/users/me")
            .with(SecurityMockMvcRequestPostProcessors.authentication(getAuthentication(user))))
        .andExpect(status().isOk());
  }

  @Test
  void getUser_NotFound() throws Exception {
    when(userService.findById(anyLong())).thenThrow(UserNotFoundException.class);

    mvc.perform(get("/api/users/me")
            .with(SecurityMockMvcRequestPostProcessors.authentication(getAuthentication(2, "USER"))))
        .andExpect(status().isNotFound());
  }

  @Test
  void getProfile_Success() throws Exception {
    when(userService.findById(anyLong())).thenReturn(user);

    mvc.perform(get("/api/users/{userId}/profile", user.getId())
            .with(SecurityMockMvcRequestPostProcessors.authentication(getAuthentication(user)))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void updateProfile_Success() throws Exception {
    UserUpdateDTO updateDTO = new UserUpdateDTO();
    updateDTO.setFirstName("Jane");
    updateDTO.setLastName("Test");
    updateDTO.setEmail("new@email.com");
    updateDTO.setPassword("NewPassword1");

    User updatedUser = user;
    updatedUser.setFirstName("Jane");
    updatedUser.setLastName("Test");
    updatedUser.setEmail("new@email.com");
    updatedUser.setPassword("NewPassword1");

    when(userService.findById(anyLong())).thenReturn(user);
    when(userService.update(any(User.class))).thenReturn(updatedUser);

    mvc.perform(patch("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(updateDTO))
            .with(SecurityMockMvcRequestPostProcessors.authentication(getAuthentication(user))))
        .andExpect(status().isOk());
  }

  @Test
  void resetPassword_ValidEmail_ReturnsAccepted() throws Exception {
    doNothing().when(userService).initiatePasswordReset("user@example.com");

    mvc.perform(post("/api/users/reset-password")
            .contentType(MediaType.TEXT_PLAIN_VALUE)
            .content("user@example.com"))
        .andExpect(status().isAccepted());
  }

  @Test
  void confirmPassword_ValidToken_ReturnsNoContent() throws Exception {
    PasswordResetDTO resetDTO = new PasswordResetDTO();
    resetDTO.setToken("valid-token");
    resetDTO.setPassword("NewPassword123");

    doNothing().when(userService).confirmPasswordReset(resetDTO.getToken(), resetDTO.getPassword());

    mvc.perform(post("/api/users/confirm-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.toJson(resetDTO)))
        .andExpect(status().isNoContent());
  }

  public static Authentication getAuthentication(User user) {
    return getAuthentication(user.getId(), user.getRole().name());
  }

  public static Authentication getAuthentication(long userId, String role) {
    return new UsernamePasswordAuthenticationToken(new AuthIdentity(userId, role), null,
        Collections.singletonList(new SimpleGrantedAuthority(role)));
  }
}
