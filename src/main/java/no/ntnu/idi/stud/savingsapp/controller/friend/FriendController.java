package no.ntnu.idi.stud.savingsapp.controller.friend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.dto.user.UserDTO;
import no.ntnu.idi.stud.savingsapp.model.notification.Notification;
import no.ntnu.idi.stud.savingsapp.model.notification.NotificationType;
import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.FriendService;
import no.ntnu.idi.stud.savingsapp.service.NotificationService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import java.util.List;
import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Slf4j
public class FriendController {

	private final UserService userService;

	private final FriendService friendService;

	private final NotificationService notificationService;

	private final ModelMapper modelMapper;

	public FriendController(UserService userService, FriendService friendService,
			NotificationService notificationService, ModelMapper modelMapper) {
		this.userService = userService;
		this.friendService = friendService;
		this.notificationService = notificationService;
		this.modelMapper = modelMapper;
	}

	@Operation(summary = "Get all friends", description = "Returns a list of all friends.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Successfully retrieved list of friends") })
	@GetMapping
	public ResponseEntity<List<UserDTO>> getFriends(@AuthenticationPrincipal AuthIdentity identity) {
		List<User> friendsUser = userService.getFriends(identity.getId());
		log.info("[FriendController:getFriends] user: {}", identity.getId());
		for (User user : friendsUser) {
			log.info("[FriendController:getFriends] friend: {}", user.getId());
		}
		return ResponseEntity.ok(convertToDto(friendsUser));
	}

	@Operation(summary = "Get friend requests",
			description = "Returns a list of all users who have sent a friend request.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Successfully retrieved friend requests") })
	@GetMapping("/requests")
	public ResponseEntity<List<UserDTO>> getFriendRequests(@AuthenticationPrincipal AuthIdentity identity) {
		List<User> friendsUser = userService.getFriendRequests(identity.getId());
		log.info("[FriendController:getFriendRequests] user: {}", identity.getId());
		for (User user : friendsUser) {
			log.info("[FriendController:getFriendRequests] friend requests: {}", user.getId());
		}
		return ResponseEntity.ok(convertToDto(friendsUser));
	}

	@Operation(summary = "Send a friend request",
			description = "Sends a new friend request to another user. A notification is sent to this user")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Friend request successfully created") })
	@PostMapping("/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public void addFriendRequest(@AuthenticationPrincipal AuthIdentity identity, @PathVariable long userId) {
		User user = userService.findById(identity.getId());
		User friend = userService.findById(userId);
		friendService.addFriendRequest(user, friend);
		Notification notification = new Notification(null, friend,
				"You have received a new friend request from " + user.getFirstName(), true,
				NotificationType.FRIEND_REQUEST, Timestamp.from(Instant.now()));
		notificationService.updateNotification(notification);
		log.info("[FriendController:addFriendRequest] from: {} to: {}", user.getId(), friend.getId());
	}

	@Operation(summary = "Accept a friend request", description = "Accepts a friend request from another user.")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Friend request successfully accepted"),
			@ApiResponse(responseCode = "404", description = "Friend request not found"), })
	@PutMapping("/{friendId}")
	public ResponseEntity<?> acceptFriendRequest(@AuthenticationPrincipal AuthIdentity identity,
			@PathVariable long friendId) {
		User user = userService.findById(identity.getId());
		User friend = userService.findById(friendId);
		Friend friendRequest = friendService.getFriendRequest(user, friend);
		if (friendRequest == null) {
			log.error("[FriendController:acceptFriendRequest] No friend request found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No friend request found.");
		}
		friendService.acceptFriendRequest(friendRequest);
		log.info("[FriendController:acceptFriendRequest] Friend request successfully accepted between: {} and: {}",
				user.getId(), friend.getId());
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Delete a friend or cancel a friend request",
			description = "Deletes an existing friend from your friend list or cancels a received friend request.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Friend successfully deleted or friend request cancelled"),
			@ApiResponse(responseCode = "404", description = "Friend or friend request not found"), })
	@DeleteMapping("/{friendId}")
	public ResponseEntity<?> deleteFriendOrFriendRequest(@AuthenticationPrincipal AuthIdentity identity,
			@PathVariable long friendId) {
		User user = userService.findById(identity.getId());
		User friend = userService.findById(friendId);

		Friend friendStatus = friendService.getFriendStatus(user, friend);
		if (friendStatus == null) {
			log.error("[FriendController:deleteFriendOrFriendRequest] No friend relationship or friend request found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No friend relationship or friend request found.");
		}

		friendService.deleteFriendOrFriendRequest(friendStatus);
		log.info("[FriendController:acceptFriendRequest] Friend request successfully deleted between: {} and: {}",
				user.getId(), friend.getId());
		return ResponseEntity.ok().build();
	}

	/**
	 * Converts a list of {@link User} objects to a list of {@link UserDTO} objects.
	 * @param users the list of users to convert
	 * @return The converted list of UserDTO objects
	 */
	private List<UserDTO> convertToDto(List<User> users) {
		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : users) {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

}
