package no.ntnu.idi.stud.savingsapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.savings.ChallengeTemplate;
import no.ntnu.idi.stud.savingsapp.model.savings.Challenge;
import no.ntnu.idi.stud.savingsapp.model.savings.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class GoalChallengeSerivceImpl {

  private ChallengeRepository challengeRepository;
  private ChallengeTemplateRepository challengeTemplateRepository;

  /**
   *
   * @param goal
   * @param user
   */
  public List<Challenge> generateSavingGoalChallenges(Goal goal, User user) {

    Map<ChallengeType, List<ChallengeTemplate>> typeChallengeListMap = new HashMap<>();
    List<Challenge> generatedChallenge = new ArrayList<>();

    for (ChallengeType challengeType : user.getChallengeTypes()) {
      typeChallengeListMap.put(challengeType,
        challengeTemplateRepository.findAllByChallengeType(challengeType));
    }

    // Needs to get time lenght of savingGoal
    LocalDateTime givenDateTime = goal.getTargetDate().toLocalDateTime();
    int daysDifferent = (int) ChronoUnit.DAYS.between(LocalDate.now(), givenDateTime );

    // Getting amount of money for savingGoal
    int targetAmount = goal.getTargetAmount();
  
    //Use templates of tasks and fill them in
    for (List<ChallengeTemplate> list : typeChallengeListMap.values()) {
      for (ChallengeTemplate challenge : list) {
          // Calculate amount of days for a challenge
          // Does not fill out exact max number of total days
          // But keeps withing range of max days
          int minDays = challenge.getChallengeMinLenght();
          int maxDays = challenge.getChallengeMaxLenght();
          int range = maxDays - minDays + 1;
          int allocatedDays = minDays + new Random().nextInt(range);

          //Calculate points for challenge
          int points = allocatedDays * 10;
  
          // Now create a savingChallenge object
          Challenge savingChallenge = new Challenge();
          savingChallenge.setPotentialSavingAmount(targetAmount * (allocatedDays / daysDifferent));
          savingChallenge.setPoints(points);
          savingChallenge.setDays(allocatedDays);
          generatedChallenge.add(savingChallenge);
      }
    }
    return generatedChallenge;
  }
}
