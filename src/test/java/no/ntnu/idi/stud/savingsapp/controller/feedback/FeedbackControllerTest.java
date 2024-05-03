package no.ntnu.idi.stud.savingsapp.controller.feedback;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idi.stud.savingsapp.UserUtil;
import no.ntnu.idi.stud.savingsapp.dto.user.FeedbackRequestDTO;
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
public class FeedbackControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  private User user;

  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(3L);
    user.setRole(Role.ADMIN);
    user.setEmail("testuser1@example.com");
  }


  @Test
  @WithMockUser
  void postFeedBackShouldReturnSuccess() throws Exception {
    FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
    feedbackRequestDTO.setEmail("user@example.com");
    feedbackRequestDTO.setMessage("I didn't like this app");
    mvc.perform(MockMvcRequestBuilders.post("/api/users/send-feedback")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(feedbackRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @WithMockUser
  void postFeedBackShouldReturnError() throws Exception {
    FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
    mvc.perform(MockMvcRequestBuilders.post("/api/users/send-feedback")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(feedbackRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
  }

  @Test
  @WithMockUser
  void getFeedBackShouldReturnListWithLength3() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/users/get-feedback")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
  }

  @Test
  @WithMockUser
  void getFeedBackShouldReturnListWithLength4() throws Exception {
    FeedbackRequestDTO feedbackRequestDTO = new FeedbackRequestDTO();
    feedbackRequestDTO.setEmail("user@example.com");
    feedbackRequestDTO.setMessage("I didn't like this app");
    mvc.perform(MockMvcRequestBuilders.post("/api/users/send-feedback")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(feedbackRequestDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mvc.perform(MockMvcRequestBuilders.get("/api/users/get-feedback")
            .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)));
  }
}
