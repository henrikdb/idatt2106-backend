package no.ntnu.idi.stud.savingsapp.controller.goal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.goal.MarkChallengeDTO;
import no.ntnu.idi.stud.savingsapp.dto.goal.CreateGoalDTO;
import no.ntnu.idi.stud.savingsapp.dto.goal.GoalDTO;
import no.ntnu.idi.stud.savingsapp.exception.ExceptionResponse;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import no.ntnu.idi.stud.savingsapp.service.GoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing goals within the application.
 */
@RestController
@RequestMapping("/api/goals")
@EnableAutoConfiguration
@Tag(name = "Goal")
public class GoalController {

  @Autowired
  private GoalService goalService;

  @Autowired
  private ChallengeService challengeService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Creates a new goal based on the provided goal data.
   *
   * @param identity The security context of the authenticated user.
   * @param request The data transfer object containing the details needed to create a goal.
   * @return ResponseEntity containing the newly created GoalDTO and the HTTP status.
   */
  @Operation(summary = "Create a goal", description = "Create a new goal")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created a goal")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GoalDTO> createGoal(@AuthenticationPrincipal AuthIdentity identity,
                                            @RequestBody CreateGoalDTO request) {
    Goal createGoal = modelMapper.map(request, Goal.class);
    Goal goal = goalService.createGoal(createGoal, identity.getId());
    GoalDTO goalDTO = modelMapper.map(goal, GoalDTO.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(goalDTO);
  }

  /**
   * Retrieves all goals associated with the authenticated user.
   *
   * @param identity The security context of the authenticated user.
   * @return ResponseEntity containing a list of GoalDTOs for the user's goals and the HTTP status.
   */
  @Operation(summary = "Get goals", description = "Get the goals of the authenticated user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the goals")
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GoalDTO>> getGoals(@AuthenticationPrincipal AuthIdentity identity) {
    List<Goal> goals = goalService.getGoals(identity.getId());
    List<GoalDTO> goalsDTO = goals.stream().map(goal -> modelMapper.map(goal, GoalDTO.class)).toList();
    return ResponseEntity.ok(goalsDTO);
  }

  @Operation(summary = "Update a challenge", description = "Update a challenge day as completed")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the challenge"),
      @ApiResponse(responseCode = "401", description = "Day is already completed or day outside of range",
          content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
  })
  @PostMapping(value = "/update-challenge", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> updateChallenge(@AuthenticationPrincipal AuthIdentity identity,
                                            @RequestBody MarkChallengeDTO request) {
    challengeService.updateProgress(identity.getId(), request.getId(),
        request.getDay(), request.getAmount());
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
