package no.ntnu.idi.stud.savingsapp.controller.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;
import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.FriendService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling friend-related requests.
 */
@RestController
@RequestMapping("/api/friends")
@Tag(name = "Friend", description = "API for managing friend relationships")
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;
    private final ModelMapper modelMapper;

    public FriendController(UserService userService, FriendService friendService, ModelMapper modelMapper) {
        this.userService = userService;
        this.friendService = friendService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all friends", description = "Returns a list of all friends.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of friends")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal AuthIdentity identity) {
        List<User> friendsUser = userService.getFriends(identity.getId());
        return ResponseEntity.ok(convertToDto(friendsUser));
    }

    @Operation(summary = "Get friend requests", description = "Returns a list of all users who have sent a friend request.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved friend requests")
    })
    @GetMapping("/requests")
    public ResponseEntity<List<UserDTO>> getFriendRequests(@AuthenticationPrincipal AuthIdentity identity) {
        List<User> friendsUser = userService.getFriendRequests(identity.getId());
        return ResponseEntity.ok(convertToDto(friendsUser));
    }

    @Operation(summary = "Send a friend request", description = "Sends a new friend request to another user.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Friend request successfully created")
    })
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFriendRequest(@AuthenticationPrincipal AuthIdentity identity, @PathVariable long userId) {
        User user = userService.findById(identity.getId());
        User friend = userService.findById(userId);
        friendService.addFriendRequest(friend, user);
    }

    @Operation(summary = "Accept a friend request", description = "Accepts a friend request from another user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Friend request successfully accepted")
    })
    @PutMapping("/{friendId}")
    public ResponseEntity<?> acceptFriendRequest(@AuthenticationPrincipal AuthIdentity identity, @PathVariable long friendId) {
        User user = userService.findById(identity.getId());
        User friend = userService.findById(friendId);
        Friend friendRequest = friendService.getFriendRequest(user, friend);
        friendService.acceptFriendRequest(friendRequest);
        return ResponseEntity.ok().build();
    }

    private List<UserDTO> convertToDto(List<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }
}
