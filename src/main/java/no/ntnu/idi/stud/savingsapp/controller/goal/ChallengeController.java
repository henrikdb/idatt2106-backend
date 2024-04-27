package no.ntnu.idi.stud.savingsapp.controller.goal;

import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.goal.ChallengeDTO;
import no.ntnu.idi.stud.savingsapp.service.ChallengeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
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

	public ResponseEntity<ChallengeDTO> getChallenge() {
		return null;
	}

}
