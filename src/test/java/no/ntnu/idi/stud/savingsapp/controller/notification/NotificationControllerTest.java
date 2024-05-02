package no.ntnu.idi.stud.savingsapp.controller.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idi.stud.savingsapp.UserUtil;
import no.ntnu.idi.stud.savingsapp.dto.notification.NotificationDTO;
import no.ntnu.idi.stud.savingsapp.model.notification.NotificationType;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class NotificationControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

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
  void getNotificationByIDShouldReturnSuccess() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/notification/1")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("You have received a new friend request"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.notificationType").value("FRIEND_REQUEST"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.unread").value(true));
  }

  @Test
  @WithMockUser
  void getNotificationsByUserShouldReturnListOf3() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/notification")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
  }

  @Test
  @WithMockUser
  void getUnreadNotificationsByUserShouldReturnListOf2() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/notification/unread")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
  }

  @Test
  @WithMockUser
  void updateNotificationsByUserShouldReturnSuccess() throws Exception {
    NotificationDTO notificationDTO = new NotificationDTO();
    notificationDTO.setId(1);
    notificationDTO.setNotificationType(NotificationType.FRIEND_REQUEST);
    notificationDTO.setMessage("You have got a new friend request");
    notificationDTO.setUnread(false);

    mvc.perform(MockMvcRequestBuilders.post("/api/notification/update")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user)))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(notificationDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/notification/1")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.unread").value(false));
  }
}
