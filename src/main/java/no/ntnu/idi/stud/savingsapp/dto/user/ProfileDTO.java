package no.ntnu.idi.stud.savingsapp.dto.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProfileDTO {

  private long id;
  private String firstName;
  private String lastName;
  private Long profileImage;
  private Long bannerImage;
  private Timestamp createdAt;
  private PointDTO point;
  private StreakDTO streak;
}
