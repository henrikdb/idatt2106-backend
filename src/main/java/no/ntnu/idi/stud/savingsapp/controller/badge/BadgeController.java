package no.ntnu.idi.stud.savingsapp.controller.badge;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.dto.badge.BadgeDTO;
import no.ntnu.idi.stud.savingsapp.model.notification.Notification;
import no.ntnu.idi.stud.savingsapp.model.notification.NotificationType;
import no.ntnu.idi.stud.savingsapp.model.user.Badge;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUserId;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.BadgeService;
import no.ntnu.idi.stud.savingsapp.service.NotificationService;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling badge related requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/badge")
@EnableAutoConfiguration
@Tag(name = "Badge")
@Slf4j
public class BadgeController {

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private UserService userService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Retrieves a badge by its id.
	 * @param badgeId The id of the badge to retrieve.
	 * @return ResponseEntity containing the BadgeDTO.
	 */
	@Operation(summary = "Get the budget", description = "Get budget by its id ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got budget"),
			@ApiResponse(responseCode = "500", description = "Badge is not found"), })
	@GetMapping(value = "/{badgeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BadgeDTO> getBadge(@PathVariable long badgeId) {
		Badge badge = badgeService.findBadgeByBadgeId(badgeId);
		BadgeDTO response = modelMapper.map(badge, BadgeDTO.class);
		log.info("[BadgeController:getBadge] badge: {}", badge.getId());
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves all the badges.
	 * @return ResponseEntity containing a list of BadgeDTOs.
	 * @apiNote This endpoint is used to fetch all the badges stored in the database.
	 */
	@Operation(summary = "Get the list of badges", description = "Get all badges stored " + "in the database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got badges") })
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BadgeDTO>> getAllBadges() {
		List<Badge> badges = badgeService.findAllBadges();
		List<BadgeDTO> badgeDTOS = badges.stream().map(badge -> modelMapper.map(badge, BadgeDTO.class)).toList();
		for (BadgeDTO badgeDTO : badgeDTOS) {
			log.info("[BadgeController:getAllBadges] badge: {}", badgeDTO.getId());
		}
		return ResponseEntity.ok(badgeDTOS);
	}

	/**
	 * Retrieves all the badges unlocked by active user.
	 * @param identity The security context of the authenticated user.
	 * @return ResponseEntity containing a list of BadgeDTOs.
	 * @apiNote This endpoint is used to fetch all the badges that are unlocked by the
	 * active user.
	 */
	@Operation(summary = "Get the list of badges", description = "Get all badges unlocked " + "by the user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got badges") })
	@GetMapping(value = "unlocked", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BadgeDTO>> getBadgesUnlockedByActiveUser(
			@AuthenticationPrincipal AuthIdentity identity) {
		List<Badge> badges = badgeService.findBadgesUnlockedByUser(identity.getId());
		List<BadgeDTO> badgeDTOS = badges.stream().map(badge -> modelMapper.map(badge, BadgeDTO.class)).toList();
		log.info("[BadgeController:getBadgesUnlockedByUser] userId: {}", identity.getId());
		for (BadgeDTO badgeDTO : badgeDTOS) {
			log.info("[BadgeController:getBadgesUnlockedByUser] badge: {}", badgeDTO.getId());
		}
		return ResponseEntity.ok(badgeDTOS);
	}

	/**
	 * Retrieves all the badges unlocked by the user.
	 * @param userId The is of the user.
	 * @return ResponseEntity containing a list of BadgeDTOs.
	 * @apiNote This endpoint is used to fetch all the badges that are unlocked by the
	 * user.
	 */
	@Operation(summary = "Get the list of badges", description = "Get all badges unlocked " + "by the user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got badges") })
	@GetMapping(value = "unlocked/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BadgeDTO>> getBadgesUnlockedByUser(@PathVariable Long userId) {
		List<Badge> badges = badgeService.findBadgesUnlockedByUser(userId);
		List<BadgeDTO> badgeDTOS = badges.stream().map(badge -> modelMapper.map(badge, BadgeDTO.class)).toList();
		log.info("[BadgeController:getBadgesUnlockedByUser] userId: {}", userId);
		for (BadgeDTO badgeDTO : badgeDTOS) {
			log.info("[BadgeController:getBadgesUnlockedByUser] badge: {}", badgeDTO.getId());
		}
		return ResponseEntity.ok(badgeDTOS);
	}

	/**
	 * Retrieves all the badges not unlocked by the active user.
	 * @param identity The security context of the authenticated user.
	 * @return ResponseEntity containing a list of BadgeDTOs.
	 * @apiNote This endpoint is used to fetch all the badges that are not unlocked by the
	 * user.
	 */
	@Operation(summary = "Get the list of badges", description = "Get all badges not " + "unlocked by the user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully got badges") })
	@GetMapping(value = "locked", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BadgeDTO>> getBadgesNotUnlockedByActiveUser(
			@AuthenticationPrincipal AuthIdentity identity) {
		List<Badge> badges = badgeService.findBadgesNotUnlockedByUser(identity.getId());
		List<BadgeDTO> badgeDTOS = badges.stream().map(badge -> modelMapper.map(badge, BadgeDTO.class)).toList();
		log.info("[BadgeController:getBadgesNotUnlockedByUser] userId: {}", identity.getId());
		for (BadgeDTO badgeDTO : badgeDTOS) {
			log.info("[BadgeController:getBadgesNotUnlockedByUser] badge: {}", badgeDTO.getId());
		}
		return ResponseEntity.ok(badgeDTOS);
	}

	/**
	 * Updates the unlocked badges for the active user by checking if a user's score
	 * matches the badges criteria, and if so adds the badge to the user and sends a
	 * notification that the badge is unlocked.
	 * @param identity The security context of the authenticated user.
	 * @return ResponseEntity containing a list of newly unlocked badges.
	 */
	@Operation(summary = "Updates unlocked badges",
			description = "Checks if a user " + "has met the criteria for unlocking badges")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated badges") })
	@GetMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUnlockedBadges(@AuthenticationPrincipal AuthIdentity identity) {
		User user = userService.findById(identity.getId());
		System.out.println(user.getPoint().getTotalEarnedPoints());
		List<Badge> badges = badgeService.findNewlyUnlockedBadgesByUserId(identity.getId());
		for (Badge badge : badges) {
			BadgeUserId badgeUserId = new BadgeUserId(badge, user);
			badgeService.addBadgeToUser(badgeUserId);
			// Send a notification that a new badge is unlocked
			Notification notification = new Notification(null, user,
					"You have unlocked a new badge " + badge.getBadgeName(), true, NotificationType.BADGE,
					Timestamp.from(Instant.now()));
			notificationService.updateNotification(notification);
		}
		List<BadgeDTO> badgeDTOS = badges.stream().map(badge -> modelMapper.map(badge, BadgeDTO.class)).toList();
		for (BadgeDTO badgeDTO : badgeDTOS) {
			log.info("[BadgeController:updateUnlockedBadges] badge: {}", badgeDTO.getId());
		}
		return ResponseEntity.ok(badgeDTOS);
	}

}