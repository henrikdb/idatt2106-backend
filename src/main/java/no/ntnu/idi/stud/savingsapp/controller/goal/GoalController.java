package no.ntnu.idi.stud.savingsapp.controller.goal;

import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.goal.CreateGoalDTO;
import no.ntnu.idi.stud.savingsapp.dto.goal.GoalDTO;
import no.ntnu.idi.stud.savingsapp.model.goal.Goal;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.GoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goal")
@EnableAutoConfiguration
@Tag(name = "goal")
public class GoalController {
  @Autowired
  private GoalService goalService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping(value = "/getGoal")
  public ResponseEntity<GoalDTO> getGoal() {

    return null;
  }

  @PostMapping(value = "/createGoal")
  public ResponseEntity<GoalDTO> createGoal(@AuthenticationPrincipal AuthIdentity identity, @RequestBody CreateGoalDTO request) {
    Goal createGoal = modelMapper.map(request, Goal.class);
    Goal goal = goalService.createGoal(createGoal, identity.getId());
    GoalDTO goalDTO = modelMapper.map(goal, GoalDTO.class);
    return ResponseEntity.status(HttpStatus.CREATED).body(goalDTO);
  }
}
