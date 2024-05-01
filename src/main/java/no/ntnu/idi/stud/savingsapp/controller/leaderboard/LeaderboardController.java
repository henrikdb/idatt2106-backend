package no.ntnu.idi.stud.savingsapp.controller.leaderboard;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.leaderboard.LeaderboardDTO;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.LeaderboardService;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling leaderboard-related requests.
 */
@RestController
@RequestMapping("/api/leaderboard")
@EnableAutoConfiguration
@Tag(name = "Leaderboard")
public class LeaderboardController {

  @Autowired
  private LeaderboardService leaderboardService;

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Retrieves the leaderboard entries based on the specified type, filter, and entry count.
   *
   * @param identity The authenticated user's identity.
   * @param type The type of leaderboard.
   * @param filter The filter for the leaderboard entries.
   * @param entryCount The number of leaderboard entries to retrieve.
   * @return ResponseEntity containing the leaderboard data.
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LeaderboardDTO> getLeaderboard(
      @AuthenticationPrincipal AuthIdentity identity,
      @RequestParam @Enumerator(value = LeaderboardType.class,
          message = "Invalid type") String type,
      @RequestParam @Enumerator(value = LeaderboardFilter.class,
          message = "Invalid filter") String filter,
      @RequestParam(defaultValue = "10", required = false) int entryCount) {
    Leaderboard leaderboard = leaderboardService.getTopUsers(
        LeaderboardType.valueOf(type), LeaderboardFilter.valueOf(filter), entryCount, identity.getId());
    LeaderboardDTO leaderboardDTO = modelMapper.map(leaderboard, LeaderboardDTO.class);
    return ResponseEntity.ok(leaderboardDTO);
  }

  /**
   * Retrieves the surrounding leaderboard entries for the authenticated user.
   *
   * @param identity The authenticated user's identity.
   * @param type The type of leaderboard.
   * @param filter The filter for the leaderboard entries.
   * @param entryCount The number of leaderboard entries to retrieve.
   * @return ResponseEntity containing the surrounding leaderboard entries.
   */
  @GetMapping(value = "/surrounding", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LeaderboardDTO> getSurrounding(
      @AuthenticationPrincipal AuthIdentity identity,
      @RequestParam @Enumerator(value = LeaderboardType.class,
          message = "Invalid type") String type,
      @RequestParam @Enumerator(value = LeaderboardFilter.class,
          message = "Invalid filter") String filter,
      @RequestParam(defaultValue = "10", required = false) int entryCount) {
    Leaderboard leaderboard = leaderboardService.getSurrounding(
        LeaderboardType.valueOf(type), LeaderboardFilter.valueOf(filter), entryCount, identity.getId());
    LeaderboardDTO leaderboardDTO = modelMapper.map(leaderboard, LeaderboardDTO.class);
    return ResponseEntity.ok(leaderboardDTO);
  }

  @Operation(summary = "Get sum of total points globally", description = "Get the sum of the total points of all users globally")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved total points")
    })
  @GetMapping(value = "/total-points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getTotalPoints() {
        long totalPoints = leaderboardService.getSumTotalEarnedPoints();
        return ResponseEntity.ok(totalPoints);
    }
}
