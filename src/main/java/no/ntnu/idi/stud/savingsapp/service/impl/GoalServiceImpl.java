package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.dto.goal.GroupUserDTO;
import no.ntnu.idi.stud.savingsapp.exception.goal.GoalNotFoundException;
import no.ntnu.idi.stud.savingsapp.exception.goal.GroupNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.model.goal.Group;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.GoalRepository;
import no.ntnu.idi.stud.savingsapp.repository.GroupRepository;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import no.ntnu.idi.stud.savingsapp.service.GoalService;
import no.ntnu.idi.stud.savingsapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing goals.
 */
@Service
@Slf4j
public class GoalServiceImpl implements GoalService {

	@Autowired
	private UserService userService;

	@Autowired
	private GoalRepository goalRepository;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private GroupRepository groupRepository;

	/**
	 * Creates a new goal for a specific user and generates associated challenges.
	 * @param goal The goal to be created, containing initial data.
	 * @param userId The ID of the user for whom the goal is being created.
	 * @return The newly created Goal, now populated with generated challenges and
	 * persisted in the database.
	 */
	@Override
	public Goal createGoal(Goal goal, List<GroupUserDTO> GroupUsers, long userId) {
		User user = userService.findById(userId);
		goal.setCreatedAt(Timestamp.from(Instant.now()));
		goal.setUser(user);
		List<Challenge> challenges = challengeService.generateChallenges(goal, user);
		goal.setChallenges(challenges);

		// Create group goal if GroupUsers were added
		if (GroupUsers != null && !GroupUsers.isEmpty()) {
			List<Goal> groupGoalList = new ArrayList<>();
			for (GroupUserDTO groupUserDTO : GroupUsers) {
				Goal groupGoal = new Goal();
				User groupUser = userService.findById(groupUserDTO.getUserId());
				groupGoal.setCreatedAt(Timestamp.from(Instant.now()));
				groupGoal.setUser(groupUser);
				List<Challenge> challengeList = challengeService.generateChallenges(groupGoal, groupUser);
				groupGoal.setChallenges(challengeList);
				goalRepository.save(groupGoal);
				groupGoalList.add(groupGoal);
			}

			Group group = new Group();
			group.setCreator(user);
			group.setGoals(groupGoalList);
			groupRepository.save(group);
		}

		return goalRepository.save(goal);
	}

	/**
	 * Retrieves all goals associated with a given user ID.
	 * @param userId The ID of the user whose goals are to be retrieved.
	 * @return A list of Goals associated with the specified user.
	 */
	@Override
	public List<Goal> getGoals(long userId) {
		return goalRepository.findByUser_Id(userId);
	}

	/**
	 * Retrieves goal associated with a given goal ID.
	 * @param goalId The ID of the user whose goals are to be retrieved.
	 * @return The goal associated with the given goal ID.
	 */
	@Override
	public Goal getGoal(long goalId) {
		Optional<Goal> optionalGoal = goalRepository.findById(goalId);
		if (optionalGoal.isPresent()) {
			return optionalGoal.get();
		}
		else {
			log.error("[GoalServiceImpl:getGoal] Goal is not found, id: {}", goalId);
			throw new GoalNotFoundException();
		}
	}

	/**
	 * @param goalId the goal that the group contains
	 * @return The group containing the goal
	 */
	@Override
	public Group getGroup(Long goalId) {
		Optional<Group> optionalGroup = groupRepository.findByGoals_Id(goalId);
		if (optionalGroup.isPresent()) {
			return optionalGroup.get();
		}
		else {
			log.error("[GoalServiceImpl:getGoal] Group is not found from goal, id: {}", goalId);
			throw new GroupNotFoundException();
		}
	}

}
