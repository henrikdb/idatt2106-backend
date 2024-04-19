package no.ntnu.idi.stud.savingsapp.dto.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public final class ProfileDTO {

  private long id;
  private String firstName;
  private String lastName;
  private Timestamp createdAt;
}
