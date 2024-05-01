package no.ntnu.idi.stud.savingsapp.dto.badge;

import lombok.Data;

@Data
public class BadgeDTO {

  private Long id;
  private String badgeName;
  private int criteria;
  private Long imageId;

}
