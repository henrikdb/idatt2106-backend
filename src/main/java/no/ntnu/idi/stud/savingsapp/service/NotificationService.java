package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.notification.Notification;
import org.springframework.stereotype.Service;

/**
 * Service interface for notification-related operations.
 */
@Service
public interface NotificationService {

	/**
	 * Retrieves a notification by its id.
	 * @param notificationId the unique identifier for the notification
	 * @return the {@link Notification} to the notificationId.
	 */
	Notification getNotificationById(long notificationId);

	/**
	 * Retrieves a list of all notifications belonging to a user.
	 * @param userId the unique identifier for the user
	 * @return a list of {@link Notification} objects belonging to the user.
	 */
	List<Notification> getNotificationsByUserId(long userId);

	/**
	 * Retrieves a list of all unread notifications belonging to a user.
	 * @param userId the unique identifier for the user
	 * @return a list of unread {@link Notification} objects belonging to the user.
	 */
	List<Notification> getUnreadNotificationsByUserId(long userId);

	/**
	 * Updates a notification by a new requested notification. Can alternatively create a
	 * new notification.
	 * @param notification the {@link Notification} object to update.
	 */
	void updateNotification(Notification notification);

}
