package no.ntnu.idi.stud.savingsapp.controller.item;

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
public class ItemControllerTest {

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
  void getInventoryShouldReturnItemWithId3() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/item/inventory")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemName").value("Item 3"));  
  }


  @Test
  @WithMockUser
  void getStoreShouldReturnEntireStore() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/item/store")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)));
  }

  @Test
  @WithMockUser
  void getStoreShouldReturnItem3AsAlreadyBought() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/item/store")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.jsonPath("$[2].alreadyBought").value(true));  
  } 

  @Test
  @WithMockUser
  void buyItemShouldNotWorkIfUserHasTooFewPoints() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/api/item/5")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  } 

  @Test
  @WithMockUser
  void buyItemShouldWorkIfUserHasEnough() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/api/item/1")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  } 

  @Test
  @WithMockUser
  void buyItemShouldAddItemToInventory() throws Exception {
    mvc.perform(MockMvcRequestBuilders.post("/api/item/1")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    mvc.perform(MockMvcRequestBuilders.get("/api/item/inventory")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].itemName").value("Item 1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(3))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].itemName").value("Item 3")); 
  } 

  @Test
  @WithMockUser
  void buyItemShouldBeMarkedAsPurchasedInStore() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/api/item/store")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].alreadyBought").value(false));

    mvc.perform(MockMvcRequestBuilders.post("/api/item/1")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    mvc.perform(MockMvcRequestBuilders.get("/api/item/store")
        .with(SecurityMockMvcRequestPostProcessors.authentication(UserUtil.getAuthentication(user))))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].alreadyBought").value(true));
  }
}
