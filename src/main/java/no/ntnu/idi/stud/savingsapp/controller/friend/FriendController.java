package no.ntnu.idi.stud.savingsapp.controller.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling friend-related requests.
 */
@RestController
@RequestMapping("/api/friend")
@EnableAutoConfiguration
@Tag(name = "Friend")
public class FriendController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all the friends of the authenticated user", description = "Get a list of unique friends with " + 
      "pending false as UserDTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully got friends")
    })
    @GetMapping(value = "/getFriends")
    public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal AuthIdentity identity) {
        List<User> friendsUser = userService.getFriends(identity.getId());
        List<UserDTO> friendsUserDTO = new ArrayList<>();

        for(User user : friendsUser) {
            friendsUserDTO.add(modelMapper.map(user, UserDTO.class));
        }

        return ResponseEntity.ok(friendsUserDTO);
    }

    @Operation(summary = "Get all the friend requests for the authenticated user", 
    description = "Get a list of users that have sent friend requests to the authenticated user " +
    " meaning pending is true.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully got friend requests")
    })
    @GetMapping(value = "/getFriendRequests")
    public ResponseEntity<List<UserDTO>> getFriendRequests(@AuthenticationPrincipal AuthIdentity identity) {
        List<User> friendsUser = userService.getFriendRequests(identity.getId());
        List<UserDTO> friendsUserDTO = new ArrayList<>();

        for(User user : friendsUser) {
            friendsUserDTO.add(modelMapper.map(user, UserDTO.class));
        }

        return ResponseEntity.ok(friendsUserDTO);
    }

    @Operation(summary = "Get all the friends of the authenticated user", description = "Get a list of unique friends with " + 
      "pending false as UserDTO")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully got friends")
    })
    @GetMapping(value = "/addFriend/{userId}")
    public ResponseEntity addFriend(
            @AuthenticationPrincipal AuthIdentity identity,
            @PathVariable long userId) {
        List<User> friendsUser = userService.getFriends(identity.getId());
        List<UserDTO> friendsUserDTO = new ArrayList<>();

        for(User user : friendsUser) {
            friendsUserDTO.add(modelMapper.map(user, UserDTO.class));
        }

        return ResponseEntity.ok(friendsUserDTO);
    }
}
