package no.ntnu.idi.stud.savingsapp.dto.user;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class FeedbackResponseDTO {

	private String id;

	private String email;

	private String message;

	private Timestamp createdAt;

}
