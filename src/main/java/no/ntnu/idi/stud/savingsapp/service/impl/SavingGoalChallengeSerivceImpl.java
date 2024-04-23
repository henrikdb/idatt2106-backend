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
import no.ntnu.idi.stud.savingsapp.model.savings.SavingChallengeTemplate;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.SavingChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.SavingGoalChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.SavingChallengeTemplateRepository;
import no.ntnu.idi.stud.savingsapp.repository.SavingGoalRepository;
import no.ntnu.idi.stud.savingsapp.service.SavingGoalChallengeService;
import org.springframework.stereotype.Service;

@Service
public class SavingGoalChallengeSerivceImpl implements SavingGoalChallengeService {

  private SavingGoalChallengeRepository SavingGoalChallengeRepository;
  private SavingChallengeRepository savingChallengeRepository;
  private SavingChallengeTemplateRepository savingChallengeTemplateRepository;

  /**
   *
   * @param savingGoal
   * @param user
   */
  public void generateSavingGoalChallenges(SavingGoal savingGoal, User user) {

    Map<ChallengeType, List<SavingChallengeTemplate>> typeChallengeListMap = new HashMap<>();

    for (ChallengeType challengeType : user.getChallengeTypes()) {
      typeChallengeListMap.put(challengeType,
        savingChallengeTemplateRepository.findAllByChallengeType(challengeType));
    }

    //Might not need this one
    for (List<SavingChallengeTemplate> list : typeChallengeListMap.values()) {
      for (SavingChallengeTemplate challenge : list) {
        if (savingChallengeRepository.findDifficultyLevelBySavingChallengeId(challenge.getId())
            .equals(user.getCommitment())) {
        }
      }
    }

    // Needs to get time lenght of savingGoal
    LocalDateTime givenDateTime = savingGoal.getTargetDate().toLocalDateTime();
    int daysDifferent = (int) ChronoUnit.DAYS.between(LocalDate.now(), givenDateTime );

    // Getting amount of money for savingGoal
    int targetAmount = savingGoal.getTargetAmount();
    List<Integer> Moneysplit = new ArrayList<Integer>();
  
    //Use templates of tasks and fill them in
    for (List<SavingChallengeTemplate> list : typeChallengeListMap.values()) {
      for (SavingChallengeTemplate challenge : list) {
          // Retrieve challenge details
          String challengeText = challenge.getChallengeText();

          // Retrive challenge type, might need
          ChallengeType challengeType = challenge.getChallengeType();

          // Calculate amount of days for a challenge
          // Does not fill out exact max number of total days
          // But keeps withing range
          int minDays = challenge.getMinDays();
          int maxDays = challenge.getMaxDays();
          int range = maxDays - minDays + 1;
          int allocatedDays = minDays + new Random().nextInt(range);

          int points = allocatedDays * 10;
  
          // Replace placeholders in challenge text
          challengeText = challengeText.replace("[placeholder]", String.valueOf(allocatedDays));
          Moneysplit.add(targetAmount * (allocatedDays / daysDifferent));
  
          // If challenge text contains a money placeholder
          if (challengeText.contains("{placeholder}")) {
              // Calculate amount of money
              //int moneyAmount = calculateMoneyAmount(challengeText, Moneysplit);
              // Replace money placeholder with amount
              challengeText = challengeText.replace("{placeholder}", String.valueOf((targetAmount * (allocatedDays / daysDifferent))) + " kr");
          }
  
          // Now create a savingChallenge object
          
      }
    }
  }
}
