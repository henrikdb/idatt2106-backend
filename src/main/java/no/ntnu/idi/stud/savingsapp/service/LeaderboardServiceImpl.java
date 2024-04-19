package no.ntnu.idi.stud.savingsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardEntry;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;
import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.FriendRepository;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the LeaderBoard interface for leaderboard-related operations.
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    /**
     * Retrieves a leaderboard containing the top users based on the specified type and filter.
     *
     * @param type The type of leaderboard to retrieve (e.g., TOTAL_POINTS, CURRENT_STREAK, TOP_STREAK).
     * @param filter The filter for who the leaderboard should contain (e.g., GLOBAL or FRIENDS).
     * @param entryCount The amount of entries you want the leaderboard to contain.
     * @param userId The ID of the user if you wanna find a leaderboard filtered on FRIENDS.
     * @return A Leaderboard object containing the top entries for the specified type and filter.
     */
    @Override
    public Leaderboard getTopUsers(LeaderboardType type, LeaderboardFilter filter, int entryCount, Long userId) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setType(type);
        leaderboard.setFilter(filter);

        List<LeaderboardEntry> entries = new ArrayList<>();
        List<User> users = new ArrayList<>();

        switch (filter) {
            case GLOBAL:
                switch (type) {
                    case TOTAL_POINTS:
                        users = userRepository.findTopUsersByTotalEarnedPoints(entryCount);
                        break;
                    case CURRENT_STREAK:
                        users = userRepository.findTopUsersByHighestCurrentStreak(entryCount);
                        break;
                    case TOP_STREAK:
                        users = userRepository.findTopUsersByHighestEverStreak(entryCount);
                        break;
                }
                for (User user : users) {
                    int score = 0;
                    switch (type) {
                        case TOTAL_POINTS:
                            score = user.getPoint().getTotalEarnedPoints();
                            break;
                        case CURRENT_STREAK:
                            score = user.getStreak().getCurrentStreak();
                            break;
                        case TOP_STREAK:
                            score = user.getStreak().getHighestStreak();
                            break;
                    }
                    entries.add(new LeaderboardEntry(user, score));
                }
                break;

            case FRIENDS:
                List<Friend> friends = friendRepository.findAllById_UserOrId_User(userId);

                // Add friends
                users = friends.stream() 
                               .map(friend -> friend.getId().getUser().getId().equals(userId) ? friend.getId().getFriend() : friend.getId().getUser())
                               .distinct()
                               .collect(Collectors.toList());
                // Add yourself
                User yourself = userRepository.findById(userId).orElse(null);
                if(yourself != null && !users.contains(yourself)) {
                    users.add(yourself);
                }
                users = users.stream()
                             .sorted(Comparator.comparing(user -> {
                                 switch (type) {
                                     case TOTAL_POINTS:
                                         return user.getPoint().getTotalEarnedPoints();
                                     case CURRENT_STREAK:
                                         return user.getStreak().getCurrentStreak();
                                     case TOP_STREAK:
                                         return user.getStreak().getHighestStreak();
                                     default:
                                         return 0;
                                 }
                             }, Comparator.reverseOrder()))
                             .limit(entryCount)
                             .collect(Collectors.toList());
                for (User user : users) {
                    int score = 0;
                    switch (type) {
                        case TOTAL_POINTS:
                            score = user.getPoint().getTotalEarnedPoints();
                            break;
                        case CURRENT_STREAK:
                            score = user.getStreak().getCurrentStreak();
                            break;
                        case TOP_STREAK:
                            score = user.getStreak().getHighestStreak();
                            break;
                    }
                    entries.add(new LeaderboardEntry(user, score));
                }
                break;
        }

        leaderboard.setEntries(entries);
        return leaderboard;
    }

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
    @Override
    public Leaderboard getSurrounding(LeaderboardType type, LeaderboardFilter filter, int entryCount, Long userId) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setType(type);
        leaderboard.setFilter(filter);

        List<LeaderboardEntry> entries = new ArrayList<>();
        List<User> users = new ArrayList<>();

        switch (filter) {
            case GLOBAL:
                // Get users surrounding the specific user (X above and X below)
                switch (type) {
                    case TOTAL_POINTS:
                        users = userRepository.findSurroundingUsersByTotalEarnedPoints(userId, entryCount);
                        break;
                    case CURRENT_STREAK:
                        users = userRepository.findSurroundingUsersByHighestCurrentStreak(userId, entryCount);
                        break;
                    case TOP_STREAK:
                        users = userRepository.findSurroundingUsersByHighestEverStreak(userId, entryCount);
                        break;
                } 
                for (User user : users) {
                    int score = 0;
                    switch (type) {
                        case TOTAL_POINTS:
                            score = user.getPoint().getTotalEarnedPoints();
                            break;
                        case CURRENT_STREAK:
                            score = user.getStreak().getCurrentStreak();
                            break;
                        case TOP_STREAK:
                            score = user.getStreak().getHighestStreak();
                            break;
                    }
                    entries.add(new LeaderboardEntry(user, score));
                }
                break;
            case FRIENDS:
        
        } 

        leaderboard.setEntries(entries);
        return leaderboard;
    }
}
