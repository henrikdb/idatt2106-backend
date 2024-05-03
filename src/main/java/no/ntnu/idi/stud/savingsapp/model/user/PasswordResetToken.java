package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Timestamp;

/**
 * Represents a password reset token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @NonNull
  @Column(name = "token", unique = true)
  private String token;

  @NonNull
  @Column(name = "created_at")
  private Timestamp createdAt;
}
