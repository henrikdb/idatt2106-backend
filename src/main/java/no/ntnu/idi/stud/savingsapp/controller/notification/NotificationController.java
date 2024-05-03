package no.ntnu.idi.stud.savingsapp.controller.notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.dto.notification.NotificationDTO;
import no.ntnu.idi.stud.savingsapp.model.notification.Notification;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling notification related requests.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/notification")
@EnableAutoConfiguration
@Tag(name = "Notification")
@Slf4j
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Retrieves a notification by its id.
   *
   * @param notificationId The id of the notification to retrieve.
   * @return ResponseEntity containing the NotificationDTO.
   */
  @Operation(summary = "Get the notification", description = "Get notification by its id ")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got notification"),
      @ApiResponse(responseCode = "500", description = "Notification is not found"),
  })
  @GetMapping(value = "/{notificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NotificationDTO> getNotification(@PathVariable long notificationId) {
    Notification notification = notificationService.getNotificationById(notificationId);
    NotificationDTO response = modelMapper.map(notification, NotificationDTO.class);
    log.info("[NotificationController:getNotification] notification: {}", response);
    return ResponseEntity.ok(response);
  }

  /**
   * Retrieves all the notifications belonging to a user.
   *
   * @param identity The security context of the authenticated user.
   * @return ResponseEntity containing a list of NotificationResponseDTO.
   */
  @Operation(summary = "Get the list of notifications", description = "Get all notifications " +
      "to a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got notifications")
  })
  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<NotificationDTO>> getNotificationByUser(@AuthenticationPrincipal AuthIdentity identity) {
    List<Notification> notifications = notificationService.getNotificationsByUserId(identity.getId());
    List<NotificationDTO> notificationDTOs = notifications.stream().map(notification -> modelMapper.map(notification, NotificationDTO.class)).toList();
    log.info("[NotificationController:getNotificationByUser] notifications: {}", notificationDTOs);
    return ResponseEntity.ok(notificationDTOs);
  }

  /**
   * Retrieves all the unread notifications to a user.
   *
   * @param identity The security context of the authenticated user.
   * @return ResponseEntity containing a list of NotificationResponseDTO.
   */
  @Operation(summary = "Get the list of unread notifications", description = "Get all unread notifications " +
      "to a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully got notifications")
  })
  @GetMapping(value = "/unread", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<NotificationDTO>> getUnreadNotificationByUser(@AuthenticationPrincipal AuthIdentity identity) {
    List<Notification> notifications = notificationService.getUnreadNotificationsByUserId(identity.getId());
    List<NotificationDTO> notificationsDTOs = notifications.stream().map(notification -> modelMapper.map(notification, NotificationDTO.class)).toList();
    log.info("[NotificationController:getUnreadNotificationByUser] notifications: {}", notificationsDTOs);
    return ResponseEntity.ok(notificationsDTOs);
  }

  /**
   * Updates a notification.
   *
   * @param identity The security context of the authenticated user.
   * @param request The notification request.
   * @return ResponseEntity.
   */
  @Operation(summary = "Updates a notification", description = "Updates a notification based on the request")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated notification"),
      @ApiResponse(responseCode = "500", description = "User is not found")
  })
  @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateNotification(@AuthenticationPrincipal AuthIdentity identity,
      @RequestBody NotificationDTO request) {
    Notification notification = modelMapper.map(request, Notification.class);
    if (notification.getCreatedAt() == null) {
      notification.setCreatedAt(Timestamp.from(Instant.now()));
    }
    User user = userService.findById(identity.getId());
    notification.setUser(user);
    notificationService.updateNotification(notification);
    log.info("[NotificationController:updateNotification] updated notification: {}", request);
    return ResponseEntity.ok().build();
  }
}
