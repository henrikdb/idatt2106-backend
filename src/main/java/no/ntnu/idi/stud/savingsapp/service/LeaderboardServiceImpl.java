package no.ntnu.idi.stud.savingsapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import no.ntnu.idi.stud.savingsapp.model.leaderboard.Leaderboard;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardEntry;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardFilter;
import no.ntnu.idi.stud.savingsapp.model.leaderboard.LeaderboardType;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;

/**
 * Implementation of the UserService interface for leaderboard-related operations.
 */
public class LeaderboardServiceImpl implements LeaderboardService{
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Leaderboard getTopUsers(LeaderboardType type, LeaderboardFilter filter, int entryCount, Long userId) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setType(type);

        List<LeaderboardEntry> entries = new ArrayList<>();
        List<User> users = new ArrayList<>();
        
        switch(filter) {
            case GLOBAL:
                switch (type) {
                    case TOTAL_POINTS:
                        users = userRepository.findTopUsersByTotalEarnedPoints(entryCount);
                        for(User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getPoint().getTotalEarnedPoints()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                    case CURRENT_STREAK:
                        users = userRepository.findTopUsersByHighestCurrentStreak(entryCount);
                        for(User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getStreak().getCurrentStreak()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                    case TOP_STREAK:
                        users = userRepository.findTopUsersByHighestEverStreak(entryCount);
                        for(User user : users) {
                            entries.add(new LeaderboardEntry(user, user.getStreak().getHighestStreak()));
                        }
                        leaderboard.setEntries(entries);
                        break;
                }
            case FRIENDS:
            switch (type) {
                case TOTAL_POINTS:
                    
                    break;
                case CURRENT_STREAK:
                    
                    break;
                case TOP_STREAK:
                    
                    break;
            }
        }
        return leaderboard;
    }   
}