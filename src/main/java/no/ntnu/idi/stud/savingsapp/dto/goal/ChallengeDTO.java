package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public final class ChallengeDTO {

  private long id;

  private int potentialSavingAmount;

  private int points;

  private int days;

  private Timestamp createdAt;

  private ChallengeTemplateDTO challengeTemplate;

  private List<DailyChallengeProgressDTO> dailyChallengeProgressList;
}
