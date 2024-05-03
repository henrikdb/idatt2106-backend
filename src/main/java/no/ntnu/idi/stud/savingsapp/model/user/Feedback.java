package no.ntnu.idi.stud.savingsapp.model.user;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;

/**
 * Represents a feedback message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  private long id;

  @NonNull
  @Column(name = "email", nullable = false)
  private String email;

  @NonNull
  @Column(name = "message", nullable = false)
  private String message;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;
}