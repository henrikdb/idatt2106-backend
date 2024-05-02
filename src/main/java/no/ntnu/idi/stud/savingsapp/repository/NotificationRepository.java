package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link NotificationRepository} entities.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  /**
   * Retrieves a notification optional by its id.
   *
   * @param notificationId the unique identifier for the notification
   * @return the optional {@link Notification} to the notificationId.
   */
  Optional<Notification> findNotificationById(long notificationId);

  /**
   * Retrieves a list of all notifications belonging to a user.
   *
   * @param userId the unique identifier for the user
   * @return a list of {@link Notification} objects belonging to the user.
   */
  @Query(value =
  "SELECT n.* FROM notification n " +
  "WHERE n.user_id = :userId"
  , nativeQuery = true)
  List<Notification> findNotificationsByUserId(@Param("userId") long userId);

  /**
   * Retrieves a list of all unread notifications belonging to a user.
   *
   * @param userId the unique identifier for the user
   * @return a list of unread {@link Notification} objects belonging to the user.
   */
  @Query(value =
  "SELECT n.* FROM notification n " +
  "WHERE n.user_id = :userId " +
      "AND n.unread = true"
  , nativeQuery = true)
  List<Notification> findUnreadNotificationsByUserId(@Param("userId") long userId);
}
