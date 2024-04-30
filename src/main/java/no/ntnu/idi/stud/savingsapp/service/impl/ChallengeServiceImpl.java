package no.ntnu.idi.stud.savingsapp.service.impl;

import no.ntnu.idi.stud.savingsapp.exception.goal.ChallengeNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.GoalNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.InvalidChallengeDayException;
import no.ntnu.idi.stud.savingsapp.exception.user.PermissionDeniedException;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.configuration.Commitment;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.ChallengeTemplate;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.goal.Progress;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeRepository;
import no.ntnu.idi.stud.savingsapp.repository.ChallengeTemplateRepository;
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

@Service
public class ChallengeServiceImpl implements ChallengeService {

  private static final Random random = new Random();

  @Autowired
  private ChallengeRepository challengeRepository;

  @Autowired
  private ChallengeTemplateRepository templateRepository;

  @Autowired
  private GoalRepository goalRepository;

  @Override
  public List<Challenge> generateChallenges(Goal goal, User user) {
    ChallengeTemplate t1 = new ChallengeTemplate();
    t1.setChallengeType(ChallengeType.NO_COFFEE);
    t1.setAmount(40);
    t1.setChallengeText("Spar {amount} kr på kaffe hver dag i [dager} dager");
    ChallengeTemplate t2 = new ChallengeTemplate();
    t2.setChallengeType(ChallengeType.EAT_PACKED_LUNCH);
    t2.setAmount(100);
    t2.setChallengeText("Spar {amount} kr ved å ta med deg matpakke");

    List<ChallengeTemplate> templates = Arrays.asList(t1, t2);
        //templateRepository.findAllByChallengeTypeIn(user.getConfiguration().getChallengeTypes());
    Collections.shuffle(templates);

    LocalDateTime targetDate = goal.getTargetDate().toLocalDateTime();
    int remainingDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), targetDate);

    List<Challenge> challenges = new ArrayList<>();
    int i = 0;

    int a = 0;

    while (remainingDays > 0) {
      int totalDays = Math.min(random.nextInt(23) + 7, remainingDays);
      int checkDays = user.getConfiguration().getCommitment().getCheckDays(totalDays);
      ChallengeTemplate template = templates.get(i++ % templates.size());
      Challenge challenge = new Challenge();
      challenge.setChallengeTemplate(template);
      challenge.setPotentialSavingAmount(template.getAmount() * checkDays);
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
      a += challenge.getPotentialSavingAmount();
      remainingDays -= totalDays;
    }
    System.out.println("a: " + a);
    return challenges;
  }

  @Override
  public void updateProgress(long userId, long id, int day, BigDecimal amount) {
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

      if (progressList.stream().anyMatch(p -> p.getDay() == day)) {
        throw new InvalidChallengeDayException("Day is already completed");
      }

      if (day > challenge.getCheckDays() || day < 1) {
        throw new InvalidChallengeDayException("Day outside of range");
      }

      Progress progressToUpdate = new Progress();
      progressToUpdate.setDay(day);
      progressToUpdate.setCompletedAt(new Timestamp(System.currentTimeMillis()));
      progressToUpdate.setAmount(amount);
      progressList.add(progressToUpdate);

      goalRepository.save(goal);
    } else {
      throw new GoalNotFoundException();
    }
  }
}
