package no.ntnu.idi.stud.savingsapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardEntry;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;
import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.FriendRepository;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface for leaderboard-related operations.
 */
public class LeaderboardServiceImpl implements LeaderboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Override
    public Leaderboard getTopUsers(LeaderboardType type, LeaderboardFilter filter, int entryCount, Long userId) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setType(type);

        List<LeaderboardEntry> entries = new ArrayList<>();
        List<User> users = new ArrayList<>();

        switch (filter) {
            case GLOBAL:
                switch (type) {
                    case TOTAL_POINTS:
                        users = userRepository.findTopUsersByTotalEarnedPoints(entryCount);
                        for (User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getPoint().getTotalEarnedPoints()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                    case CURRENT_STREAK:
                        users = userRepository.findTopUsersByHighestCurrentStreak(entryCount);
                        for (User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getStreak().getCurrentStreak()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                    case TOP_STREAK:
                        users = userRepository.findTopUsersByHighestEverStreak(entryCount);
                        for (User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getStreak().getHighestStreak()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                }
            case FRIENDS:

                List<Friend> friends = friendRepository.findAllById_UserOrId_User(userId);
                List<User> user_temp = new ArrayList<>();

                // Get a list containing only your friends
                for (Friend friend : friends) {
                    if (friend.getId().getUser().getId().equals(userId)) {
                        user_temp.add(friend.getId().getFriend());
                    } else {
                        user_temp.add(friend.getId().getUser());
                    }
                }
                switch (type) {
                    case TOTAL_POINTS:
                        users = users.stream()
                                // Sort users by the highest points amount in descending order
                                .sorted(Comparator.comparing(user -> user.getPoint().getTotalEarnedPoints(),
                                        Comparator.reverseOrder()))
                                // Limit the list to the top X users
                                .limit(entryCount)
                                // Collect the results into users
                                .collect(Collectors.toList());

                        for (User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getPoint().getTotalEarnedPoints()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                    case CURRENT_STREAK:
                        users = users.stream()
                                // Sort users by the current highest streak in descending order
                                .sorted(Comparator.comparing(user -> user.getStreak().getCurrentStreak(),
                                        Comparator.reverseOrder()))
                                // Limit the list to the top X users
                                .limit(entryCount)
                                // Collect the results into users
                                .collect(Collectors.toList());

                        for (User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getStreak().getCurrentStreak()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                    case TOP_STREAK:
                        users = users.stream()
                                // Sort users by the current highest streak in descending order
                                .sorted(Comparator.comparing(user -> user.getStreak().getHighestStreak(),
                                        Comparator.reverseOrder()))
                                // Limit the list to the top X users
                                .limit(entryCount)
                                // Collect the results into users
                                .collect(Collectors.toList());

                        users = userRepository.findTopUsersByHighestEverStreak(entryCount);
                        for (User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getStreak().getHighestStreak()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                }
        }
        return leaderboard;
    }
}
