package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.goal.participant.Participant;
import no.ntnu.idi.stud.savingsapp.model.goal.participant.ParticipantRole;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.service.GoalService;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {

	@Autowired
	private UserService userService;

	@Autowired
	private GoalRepository goalRepository;

	@Autowired
	private ChallengeSerivceImpl goalChallengeSerivce;

	public Goal createGoal(Goal goal, Long userID) {
		goal.setChallenges(goalChallengeSerivce.generateSavingGoalChallenges(goal, userService.findById(userID)));
		goal.setCreatedAt(Timestamp.from(Instant.now()));
		Participant creator = new Participant();
		creator.setUser(userService.findById(userID));
		creator.setRole(ParticipantRole.CREATOR);

		goal.setParticipants(Arrays.asList(creator));

		System.out.println(goal);

		return goalRepository.save(goal);
	}

	public List<Goal> getGoalList(Long userID) {
		return goalRepository.findByParticipants_User_Id(userID);
	}

}
