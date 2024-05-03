package no.ntnu.idi.stud.savingsapp.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.exception.notification.NotificationNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.notification.Notification;
import no.ntnu.idi.stud.savingsapp.repository.NotificationRepository;
import no.ntnu.idi.stud.savingsapp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the NotificationService interface for handling notifications.
 */
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	/**
	 * Retrieves a notification by its id.
	 * @param notificationId the unique identifier for the notification
	 * @return the {@link Notification} to the notificationId.
	 * @throws NotificationNotFoundException if notification is not found.
	 */
	@Override
	public Notification getNotificationById(long notificationId) {
		Optional<Notification> optionalNotification = notificationRepository.findNotificationById(notificationId);
		if (optionalNotification.isPresent()) {
			return optionalNotification.get();
		}
		else {
			log.error("[NotificationServiceImpl:getNotificationById] notification does not exists, id: {}",
					notificationId);
			throw new NotificationNotFoundException();
		}
	}

	/**
	 * Retrieves a list of all notifications belonging to a user.
	 * @param userId the unique identifier for the user
	 * @return a list of {@link Notification} objects belonging to the user.
	 */
	@Override
	public List<Notification> getNotificationsByUserId(long userId) {
		return notificationRepository.findNotificationsByUserId(userId);
	}

	/**
	 * Retrieves a list of all unread notifications belonging to a user.
	 * @param userId the unique identifier for the user
	 * @return a list of unread {@link Notification} objects belonging to the user.
	 */
	@Override
	public List<Notification> getUnreadNotificationsByUserId(long userId) {
		return notificationRepository.findUnreadNotificationsByUserId(userId);
	}

	/**
	 * Updates a notification by a new requested notification. Can alternatively create a
	 * new notification.
	 * @param notification the {@link Notification} object to update.
	 */
	@Override
	public void updateNotification(Notification notification) {
		notificationRepository.save(notification);
	}

}