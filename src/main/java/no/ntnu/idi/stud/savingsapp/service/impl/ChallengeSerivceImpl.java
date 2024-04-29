package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.goal.ChallengeTemplate;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeTemplateRepository;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChallengeSerivceImpl implements ChallengeService {

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private ChallengeTemplateRepository challengeTemplateRepository;

  /**
   *
   * @param goal
   * @param user
   */
  public List<Challenge> generateSavingGoalChallenges (Goal goal, User user) {

    Map<ChallengeType, List<ChallengeTemplate>> typeChallengeListMap = new HashMap<>();
    List<Challenge> generatedChallenge = new ArrayList<>();

    for (ChallengeType challengeType : user.getConfiguration().getChallengeTypes()) {
      typeChallengeListMap.put(challengeType,
        challengeTemplateRepository.findAllByChallengeType(challengeType));
    }

    // Needs to get time lenght of savingGoal
    LocalDateTime givenDateTime = goal.getTargetDate().toLocalDateTime();
    int daysDifferent = (int) ChronoUnit.DAYS.between(LocalDate.now(), givenDateTime );

    // Getting amount of money for savingGoal
    double targetAmount = (double) goal.getTargetAmount();
  
    //Use templates of tasks and fill them in
    for (List<ChallengeTemplate> list : typeChallengeListMap.values()) {
      Collections.shuffle(list); // shuffle the list
      int count = 0;
      for (ChallengeTemplate challenge : list) {
        if (count >= 2) {
          break; // stop after 2 iterations
        }
        // Calculate amount of days for a challenge
        // Does not fill out exact max number of total days
        // But keeps withing range of max days
        int minDays = challenge.getChallengeMinLength();
        int maxDays = challenge.getChallengeMaxLength();
        int range = maxDays - minDays + 1;
        int allocatedDays = minDays + new Random().nextInt(range);

        //Calculate amount
        double amount = targetAmount / ((double) typeChallengeListMap.size() * 2);

        //Calculate points for challenge
        int points = allocatedDays * 10;

        // Now create a savingChallenge object
        Challenge savingChallenge = new Challenge();
        savingChallenge.setPotentialSavingAmount((int)amount);
        savingChallenge.setPoints(points);
        savingChallenge.setDays(allocatedDays);
        savingChallenge.setChallengeTemplate(challenge);
        savingChallenge.setCreatedAt(Timestamp.from(Instant.now()));
        generatedChallenge.add(savingChallenge);
        count++; // increment the counter
      }
    }

    Collections.shuffle(generatedChallenge);
    return generatedChallenge;
  }
}
