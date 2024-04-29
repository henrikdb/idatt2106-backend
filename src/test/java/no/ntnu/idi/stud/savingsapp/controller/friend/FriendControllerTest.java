package no.ntnu.idi.stud.savingsapp.controller.friend;

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
public class FriendControllerTest {

  @Autowired
  private MockMvc mvc;

  private User user;

  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(3L);
    user.setRole(Role.USER);
    user.setEmail("testuser1@example.com");
  }


  @Test
  void getFriendsShouldReturnAllFriends() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/friends")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(7));
  }

  @Test
  @WithMockUser
  void getFriendRequestsShouldReturnAllFriendRequests() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/friends/requests")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(14));
  }

  @Test
  @WithMockUser
  void putAcceptFriendRequestShouldAddFriend() throws Exception {
    mvc.perform(MockMvcRequestBuilders.put("/api/friends/14")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk());
      
    mvc.perform(MockMvcRequestBuilders.get("/api/friends")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(14))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(7));
  }

  @Test
  @WithMockUser
  void postAddFriendRequestShouldAddFriendRequest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/api/friends/3")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isCreated());
      
    mvc.perform(MockMvcRequestBuilders.get("/api/friends/requests")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
  }
}
