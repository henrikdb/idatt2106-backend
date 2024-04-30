package no.ntnu.idi.stud.savingsapp.controller.goal;

import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.goal.ChallengeDTO;
import no.ntnu.idi.stud.savingsapp.dto.goal.ChallengeUpdateStateDTO;
import no.ntnu.idi.stud.savingsapp.model.goal.Challenge;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge")
@EnableAutoConfiguration
@Tag(name = "GoalChallenge")
public class ChallengeController {
  @Autowired
  private ChallengeService challengeService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping(value = "/updateChallengeState")
  public ResponseEntity<Void> updateChallengeState(@AuthenticationPrincipal AuthIdentity identity, @RequestBody ChallengeUpdateStateDTO request) {

    challengeService.updateProgress(identity.getId(), request.getChallengeId(), request.getChallengeDay());

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
