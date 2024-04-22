package no.ntnu.idi.stud.savingsapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository; 

@Repository // Added annotation
public interface LeaderboardRepository extends JpaRepository<Long, Long>  {
    
    @Query(value =
            "SELECT RANK() OVER (ORDER BY s.total_earned_points DESC) " +
            "FROM user " +
            "JOIN streak s ON u.streak_id = s.streak_id " +
            "WHERE user_id = :userId",
            nativeQuery = true)
    int findUserRankByTotalEarnedPoints(@Param("userId") Long userId);

    @Query(value =
            "SELECT RANK() OVER (ORDER BY s.total_earned_points DESC) " +
            "FROM user " +
            "JOIN streak s ON u.streak_id = s.streak_id " +
            "WHERE user_id = :userId",
            nativeQuery = true)
    int findUserRankByCurrentStreak(@Param("userId") Long userId);
}
