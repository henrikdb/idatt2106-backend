package no.ntnu.idi.stud.savingsapp.dto.notification;

import java.sql.Timestamp;
import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.notification.NotificationType;

@Data
public class NotificationDTO {

	private long id;

	private String message;

	private boolean unread;

	private NotificationType notificationType;

	private Timestamp createdAt;

}
