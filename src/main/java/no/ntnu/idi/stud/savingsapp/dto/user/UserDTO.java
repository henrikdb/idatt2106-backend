package no.ntnu.idi.stud.savingsapp.dto.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public final class UserDTO {

  private long id;
  private String firstName;
  private String lastName;
  private Long profileImage;
  private String email;
  private Timestamp createdAt;
  private String role;
}
