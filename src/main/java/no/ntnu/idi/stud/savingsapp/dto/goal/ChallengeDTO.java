package no.ntnu.idi.stud.savingsapp.dto.goal;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

import java.sql.Timestamp;
import java.util.List;

@Data
public final class ChallengeDTO {

  private long id;

  private int potentialSavingAmount;

  private int points;

  private int days;

  private Timestamp startDate;

  private Timestamp endDate;

  private Timestamp createdAt;

  private ChallengeTemplateDTO challengeTemplate;

  private List<ProgressDTO> progressList;
}
