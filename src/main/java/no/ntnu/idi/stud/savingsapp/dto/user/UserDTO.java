package no.ntnu.idi.stud.savingsapp.dto.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {

  private long id;
  private String firstName;
  private String lastName;
  private Long profileImage;
  private Long bannerImage;
  private String email;
  private Timestamp createdAt;
  private String role;
  private String subscriptionLevel;
  private BankAccountResponseDTO checkingAccountBBAN;
  private BankAccountResponseDTO savingsAccountBBAN;
  private PointDTO point;
  private StreakDTO streak;
}
