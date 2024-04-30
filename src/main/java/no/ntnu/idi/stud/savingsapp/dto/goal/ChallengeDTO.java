package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public final class ChallengeDTO {

  private long id;
  private int amount;
  private int points;
  private int checkDays;
  private int totalDays;
  private Timestamp startDate;
  private Timestamp endDate;
  private ChallengeTemplateDTO challengeTemplate;
  private List<ProgressDTO> progressList;
}
