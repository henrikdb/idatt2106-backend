package no.ntnu.idi.stud.savingsapp.controller.badge;

import no.ntnu.idi.stud.savingsapp.UserUtil;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BadgeControllerTest {

  @Autowired
  private MockMvc mvc;
  private User user;

  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(1L);
    user.setRole(Role.USER);
    user.setEmail("testuser1@example.com");
  }

  @Test
  @WithMockUser
  void getBadgeShouldReturnSuccess() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/badge/1")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.badgeName").value("Saving Champ"));
  }

  @Test
  @WithMockUser
  void getBadgeShouldReturnError() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/badge/4")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
  }

  @Test
  @WithMockUser
  void getBadgesShouldReturnListOf3() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/badge")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
  }

  @Test
  @WithMockUser
  void getUnlockedBadgesShouldReturnListOf2() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/badge/unlocked")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
  }

  @Test
  @WithMockUser
  void getNotUnlockedBadgesShouldReturnListOf1() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/badge/locked")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
  }

  @Test
  @WithMockUser
  void updateUnlockedBadgesShouldReturnListOf1() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/badge/update")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
  }
}