package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

import no.ntnu.idi.stud.savingsapp.exception.goal.ChallengeNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.GoalNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.InvalidChallengeDayException;
import no.ntnu.idi.stud.savingsapp.exception.user.PermissionDeniedException;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.goal.ChallengeTemplate;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Progress;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeTemplateRepository;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private ChallengeTemplateRepository challengeTemplateRepository;

  @Autowired
  private GoalRepository goalRepository;

  /**
   *
   * @param goal
   * @param user
   */
  public List<Challenge> generateSavingGoalChallenges (Goal goal, User user) {

    List<Challenge> generatedChallenge = new ArrayList<>();

    for (ChallengeType challengeType : user.getConfiguration().getChallengeTypes()) {
      typeChallengeListMap.put(challengeType,
        challengeTemplateRepository.findAllByChallengeType(challengeType));
    }

    // Needs to get time lenght of savingGoal
    LocalDateTime givenDateTime = goal.getTargetDate().toLocalDateTime();
    int daysDifferent = (int) ChronoUnit.DAYS.between(LocalDate.now(), givenDateTime);

    // Getting amount of money for savingGoal
    double targetAmount = goal.getTargetAmount();
    double amountPerDay = targetAmount / daysDifferent;

    //Use templates of tasks and fill them in
    Timestamp currentDate = Timestamp.from(Instant.now());
    while (true) {
      for (ChallengeTemplate challenge : templates) {
        // Calculate amount of days for a challenge
        // Does not fill out exact max number of total days
        // But keeps withing range of max days
        int minDays = challenge.getChallengeMinLength();
        int maxDays = challenge.getChallengeMaxLength();
        int range = maxDays - minDays + 1;
        int allocatedDays = minDays + new Random().nextInt(range);

        //Calculate amount
        double amount = amountPerDay * allocatedDays;

        //Calculate points for challenge
        int points = allocatedDays * 10;

        // Now create a savingChallenge object
        Challenge savingChallenge = new Challenge();
        savingChallenge.setPotentialSavingAmount((int) amount);
        savingChallenge.setPoints(points);
        savingChallenge.setDays(allocatedDays);

        //Calculate days in dates
        if (generatedChallenge.isEmpty()) {
          Timestamp startDate = Timestamp.from(Instant.now());
          savingChallenge.setStartDate(startDate);
          savingChallenge.setEndDate(Timestamp.from(startDate.toInstant().plus(allocatedDays, ChronoUnit.DAYS)));
        } else {
          Timestamp startDate = Timestamp.from(generatedChallenge.get(generatedChallenge.size() - 1).getEndDate().toInstant().plus(1, ChronoUnit.DAYS));
          savingChallenge.setStartDate(startDate);
          savingChallenge.setEndDate(Timestamp.from(startDate.toInstant().plus(allocatedDays, ChronoUnit.DAYS)));
        }

        savingChallenge.setChallengeTemplate(challenge);
        savingChallenge.setCreatedAt(Timestamp.from(Instant.now()));
        generatedChallenge.add(savingChallenge);
        count++; // increment the counter
      }
      if (!generatedChallenge.isEmpty() && generatedChallenge.get(generatedChallenge.size() - 1).getEndDate().toInstant().equals(goal.getTargetDate().toInstant())) {
        break;
      }
    }
    Collections.shuffle(generatedChallenge);
    return generatedChallenge;
  }

  @Override
  public void updateProgress(long userId, long id, int day) {
    Optional<Goal> goalOptional = goalRepository.findByChallenges_Id(id);
    if (goalOptional.isPresent()) {
      Goal goal = goalOptional.get();

      if (goal.getUser().getId() != userId) {
        throw new PermissionDeniedException();
      } 

      Challenge challenge = goal.getChallenges().stream().filter(c -> c.getId() == id).findFirst().orElse(null);
      if (challenge == null) {
        throw new ChallengeNotFoundException();
      }
      List<Progress> progressList = challenge.getProgressList();

      if (progressList.stream().anyMatch(p -> p.getChallengeDay() == day)) {
        throw new InvalidChallengeDayException("Day is already completed");
      }

      if (day > challenge.getDays() || day < 1) {
        throw new InvalidChallengeDayException("Day outside of range");
      }

      Progress progressToUpdate = new Progress();
      progressToUpdate.setChallengeDay(day);
      progressToUpdate.setCompletedAt(new Timestamp(System.currentTimeMillis()));
      progressList.add(progressToUpdate);

      goalRepository.save(goal);
    } else {
      throw new GoalNotFoundException();
    }
  }
}
