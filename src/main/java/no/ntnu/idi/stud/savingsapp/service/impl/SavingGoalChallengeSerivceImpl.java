package no.ntnu.idi.stud.savingsapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingChallenge;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoal;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.SavingChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.SavingGoalChallengeRepository;
import no.ntnu.idi.stud.savingsapp.service.SavingGoalChallengeService;
import org.springframework.stereotype.Service;

@Service
public class SavingGoalChallengeSerivceImpl implements SavingGoalChallengeService {

  private SavingGoalChallengeRepository SavingGoalChallengeRepository;
  private SavingChallengeRepository savingChallengeRepository;

  /**
   *
   * @param savingGoal
   * @param user
   */
  public void generateSavingGoalChallenges(SavingGoal savingGoal, User user) {

    Map<ChallengeType, List<SavingChallenge>> typeChallengeListMap = new HashMap<>();

    for (ChallengeType challengeType : user.getChallengeTypes()) {
      typeChallengeListMap.put(challengeType,
          savingChallengeRepository.findAllByChallengeType(challengeType));
    }

    for (List<SavingChallenge> list : typeChallengeListMap.values()) {
      for (SavingChallenge challenge : list) {
        if (savingChallengeRepository.findDifficultyLevelBySavingChallengeId(challenge.getId())
            .equals(user.getCommitment())) {

        }
      }
    }



  }
}
