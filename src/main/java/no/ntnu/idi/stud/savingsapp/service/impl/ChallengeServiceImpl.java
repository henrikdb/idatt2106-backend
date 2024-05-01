package no.ntnu.idi.stud.savingsapp.service.impl;

import no.ntnu.idi.stud.savingsapp.exception.goal.ChallengeNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.GoalNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.InvalidChallengeDayException;
import no.ntnu.idi.stud.savingsapp.exception.user.PermissionDeniedException;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.ChallengeTemplate;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.goal.Progress;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Implementation of ChallengeService to manage challenges within goals.
 */
@Service
public class ChallengeServiceImpl implements ChallengeService {

  private static final Random random = new Random();

  @Autowired
  private GoalRepository goalRepository;

  /**
   * Generates a list of challenges for a given goal based on the user's preferences and goal's target date.
   * Each challenge is generated based on predefined templates and adjusted for the duration of the goal.
   *
   * @param goal The goal for which to generate challenges.
   * @param user The user who owns the goal.
   * @return A list of generated Challenge objects.
   */
  @Override
  public List<Challenge> generateChallenges(Goal goal, User user) {
    ChallengeTemplate t1 = new ChallengeTemplate();
    t1.setChallengeType(ChallengeType.NO_COFFEE);
    t1.setAmount(BigDecimal.valueOf(40));
    t1.setText("Spar {unit_amount} kr hver gang du kjøper kaffe, totalt {checkDays} ganger over " +
        "{totalDays} dager. Dette gir deg en total besparelse på {total_amount} kr.");
    ChallengeTemplate t2 = new ChallengeTemplate();
    t2.setChallengeType(ChallengeType.EAT_PACKED_LUNCH);
    t2.setAmount(BigDecimal.valueOf(100));
    t2.setText("Spar {amount} kr ved å ta med deg matpakke");

    List<ChallengeTemplate> templates = Arrays.asList(t1, t2);
        //templateRepository.findAllByChallengeTypeIn(user.getConfiguration().getChallengeTypes());
    Collections.shuffle(templates);

    LocalDateTime targetDate = goal.getTargetDate().toLocalDateTime();
    int remainingDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), targetDate);

    List<Challenge> challenges = new ArrayList<>();
    int i = 0;

    while (remainingDays > 0) {
      int totalDays = Math.min(random.nextInt(23) + 7, remainingDays);
      int checkDays = user.getConfiguration().getCommitment().getCheckDays(totalDays);
      ChallengeTemplate template = templates.get(i++ % templates.size());
      Challenge challenge = new Challenge();
      challenge.setTemplate(template);
      challenge.setPotentialAmount(template.getAmount());
      challenge.setPoints(totalDays * 10);
      challenge.setCheckDays(checkDays);
      challenge.setTotalDays(totalDays);

      if (challenges.isEmpty()) {
        challenge.setStartDate(goal.getCreatedAt());
      } else {
        Timestamp lastEndDate = challenges.get(challenges.size() - 1).getEndDate();
        LocalDate localDate = lastEndDate.toLocalDateTime().toLocalDate().plusDays(1);
        Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
        challenge.setStartDate(timestamp);
      }
      Timestamp lastEndDate = challenge.getStartDate();
      LocalDate localDate = lastEndDate.toLocalDateTime().toLocalDate().plusDays(totalDays);
      Timestamp timestamp = Timestamp.valueOf(localDate.atStartOfDay());
      challenge.setEndDate(timestamp);

      if (challenge.getEndDate().after(goal.getTargetDate())) {
        break;
      }

      challenges.add(challenge);
      remainingDays -= totalDays;
    }
    return challenges;
  }

  /**
   * Updates the progress for a specific challenge on a specified day with a given amount.
   * Validates user permissions, challenge existence, and the validity of the specified day.
   *
   * @param userId The ID of the user updating the challenge.
   * @param id The ID of the challenge to update.
   * @param day The day of the challenge to mark as completed.
   * @param amount The amount saved or achieved on the specified day.
   * @throws PermissionDeniedException if the user does not own the goal associated with the challenge.
   * @throws ChallengeNotFoundException if the challenge cannot be found within the goal.
   * @throws InvalidChallengeDayException if the specified day is invalid or already completed.
   * @throws GoalNotFoundException if the goal associated with the challenge is not found.
   */
  @Override
  public void updateProgress(long userId, long id, int day, BigDecimal amount) {
    Goal goal = getGoalByChallengeId(id);
    if (goal.getUser().getId() != userId) {
      throw new PermissionDeniedException();
    }

    Challenge challenge = goal.getChallenges().stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    if (challenge == null) {
      throw new ChallengeNotFoundException();
    }
    List<Progress> progressList = challenge.getProgressList();

    if (progressList.stream().anyMatch(p -> p.getDay() == day)) {
      throw new InvalidChallengeDayException("Day is already completed");
    }
    if (day > challenge.getCheckDays() || day < 1) {
      throw new InvalidChallengeDayException("Day outside of range");
    }

    Progress progress = new Progress();
    progress.setDay(day);
    progress.setCompletedAt(Timestamp.from(Instant.now()));
    progress.setAmount(amount);
    progressList.add(progress);

    goalRepository.save(goal);
  }

  /**
   * Updates the potential saving amount for a specific challenge within a goal.
   * This method ensures that only the owner of the goal can update the challenge, and verifies that the challenge exists.
   *
   * @param userId The ID of the user attempting to update the saving amount.
   * @param id The ID of the challenge whose saving amount is being updated.
   * @param amount The new saving amount to be set for the challenge.
   * @throws PermissionDeniedException if the user trying to update the saving amount does not own the goal.
   * @throws ChallengeNotFoundException if no challenge with the given ID can be found within the goal.
   * @throws GoalNotFoundException if no goal containing the specified challenge can be found.
   */
  @Override
  public void updateSavingAmount(long userId, long id, BigDecimal amount) {
    Goal goal = getGoalByChallengeId(id);
    if (goal.getUser().getId() != userId) {
      throw new PermissionDeniedException();
    }

    Challenge challenge = goal.getChallenges().stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    if (challenge == null) {
      throw new ChallengeNotFoundException();
    }
    challenge.setPotentialAmount(amount);

    goalRepository.save(goal);
  }

  /**
   * Retrieves a goal that contains a specific challenge identified by the challenge ID.
   * This method is useful for operations requiring access to a goal based on one of its challenges,
   * ensuring the challenge's existence within the goal structure.
   *
   * @param challengeId The ID of the challenge whose goal is to be retrieved.
   * @return The Goal containing the specified challenge.
   * @throws GoalNotFoundException If no goal containing the specified challenge can be found.
   */
  private Goal getGoalByChallengeId(long challengeId) {
    Optional<Goal> goalOptional = goalRepository.findByChallenges_Id(challengeId);
    if (goalOptional.isPresent()) {
      return goalOptional.get();
    } else {
      throw new ChallengeNotFoundException();
    }
  }
}
