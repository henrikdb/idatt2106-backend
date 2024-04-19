package no.ntnu.idi.stud.savingsapp.service;

import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;

/**
 * Service interface for leaderboard-related operations.
 */
@Service
public interface LeaderboardService {
    /**
     * Retrieves a leaderboard containing the top users based on the specified type and filter.
     *
     * @param type The type of leaderboard to retrieve (e.g., TOTAL_POINTS, CURRENT_STREAK, TOP_STREAK).
     * @param filter The filter for who the leaderboard should contain (e.g., GLOBAL or FRIENDS).
     * @param entryCount The amount of entries you want the leaderboard to contain.
     * @param userId The ID of the user if you wanna find a leaderboard filtered on FRIENDS.
     * @return A Leaderboard object containing the top entries for the specified type and filter.
     */
    Leaderboard getTopUsers(LeaderboardType type, LeaderboardFilter filter, int entryCount, Long userId);

    /**
     * Retrieves a leaderboard containing the users surrounding a user on the specified type and filter.
     * User with ID userID will be in the middle.
     *
     * @param type The type of leaderboard to retrieve (e.g., TOTAL_POINTS, CURRENT_STREAK, TOP_STREAK).
     * @param filter The filter for who the leaderboard should contain (e.g., GLOBAL or FRIENDS).
     * @param entryCount The amount of entries you want the leaderboard to contain.
     * @param userId The ID of the user you want to be in the middle of the leaderboard.
     * @return A Leaderboard object containing the specified user entry in the middle and entries surrounding it for the specified type and filter.
     */
    Leaderboard getSurrounding(LeaderboardType type, LeaderboardFilter filter, int entryCount, Long userId);

}