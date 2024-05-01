package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.model.user.SubscriptionLevel;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

/**
 * Repository interface for {@link User} entities.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Finds a user by their email.
   *
   * @param email The email of the user to be found
   * @return An optional containing the user if found, otherwise empty.
   */
  Optional<User> findByEmail(String email);

  /**
   * Finds users with names containing provided string.
   *
   * @param firstName The string containing any of the characters present in the first name of a
   *                  user.
   * @param lastName  The string containing any of the characters present in the first name of a
   *    *             user.
   * @return A list of users with names containing the provided string.
   */
  List<User> findUserByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
      String firstName, String lastName);

  /**
   * Finds a user by their BankID subject identifier.
   * This method queries the database to locate a user entity associated with the specified BankID subject identifier.
   * The 'sub' is a unique identifier assigned by BankID to a user and is used to match a user in the application's database.
   *
   * @param sub The unique subject identifier provided by BankID for a user.
   * @return An {@link Optional<User>} containing the user if found, or an empty Optional if no user is associated with the given sub.
   */
  Optional<User> findByBankIdSub(String sub);

  /**
   * Finds the top X users with the highest total earned points.
   * @param entryCount The maximum number of users to return.
   * @return A list of users sorted by total earned points in descending order.
   */
  @Query(value = 
  "SELECT u.* " + 
  "FROM user u " +
  "JOIN point p ON u.point_id = p.point_id " +
  "ORDER BY p.total_earned_points DESC " +
  "LIMIT :entryCount", nativeQuery = true)  
  List<User> findTopUsersByTotalEarnedPoints(@Param("entryCount") Integer entryCount);

  /**
   * Finds the top X users with the highest ever streak.
   * @param entryCount The maximum number of users to return.
   * @return A list of users sorted by highest ever streak in descending order.
   */
  @Query(value = 
  "SELECT u.* " + 
  "FROM user u " +
  "JOIN streak s ON u.streak_id = s.streak_id " +
  "ORDER BY s.highest_streak DESC " +
  "LIMIT :entryCount", nativeQuery = true)  
  List<User> findTopUsersByHighestEverStreak(@Param("entryCount") Integer entryCount);

  /**
   * Finds the top X users with the highest current streak.
   * @param entryCount The maximum number of users to return.
   * @return A list of users sorted by highest current streak in descending order.
   */
  @Query(value = 
  "SELECT u.* " +
  "FROM user u " +
  "JOIN streak s ON u.streak_id = s.streak_id " +
  "ORDER BY s.current_streak DESC " +
  "LIMIT :entryCount", nativeQuery = true)
  List<User> findTopUsersByHighestCurrentStreak(@Param("entryCount") Integer entryCount);

  /**
   * Finds the top X friends with the highest total earned points.
   * @param entryCount The maximum number of friends to return.
   * @return A list of friends sorted by total earned points in descending order.
   */
  @Query(value = 
  "SELECT u.* FROM ( " +
  "SELECT DISTINCT u.user_id FROM user u " +
  "JOIN friend f ON u.user_id = f.user_id OR u.user_id = f.friend_id " +
  "WHERE (f.user_id = :userId OR f.friend_id = :userId) AND f.pending IS FALSE" + 
  ") AS distinct_users " +
  "JOIN user u ON u.user_id = distinct_users.user_id " +
  "JOIN point p ON u.point_id = p.point_id " +
  "ORDER BY p.total_earned_points DESC " +
  "LIMIT :entryCount", nativeQuery = true)
  List<User> findTopFriendsByTotalEarnedPoints(@Param("userId") Long userId, @Param("entryCount") Integer entryCount);

  /**
   * Finds the top X friends with the highest ever streak.
   * @param entryCount The maximum number of friends to return.
   * @return A list of friends sorted by highest ever streak in descending order.
   */
  @Query(value = 
  "SELECT u.* FROM ( " +
  "SELECT DISTINCT u.user_id FROM user u " +
  "JOIN friend f ON (u.user_id = f.user_id OR u.user_id = f.friend_id) " +
  "WHERE (f.user_id = :userId OR f.friend_id = :userId) AND f.pending IS FALSE" + 
  ") AS distinct_users " +
  "JOIN user u ON u.user_id = distinct_users.user_id " +
  "JOIN streak s ON u.streak_id = s.streak_id " +
  "ORDER BY s.highest_streak DESC " +
  "LIMIT :entryCount", nativeQuery = true)
  List<User> findTopFriendsByHighestEverStreak(@Param("userId") Long userId, @Param("entryCount") Integer entryCount);

  /**
   * Finds the top X friends with the highest current streak.
   * @param entryCount The maximum number of friends to return.
   * @return A list of friends sorted by highest current streak in descending order.
   */
  @Query(value = 
  "SELECT u.* FROM ( " +
  "SELECT DISTINCT u.user_id FROM user u " +
  "JOIN friend f ON (u.user_id = f.user_id OR u.user_id = f.friend_id) " +
  "WHERE (f.user_id = :userId OR f.friend_id = :userId) AND f.pending IS FALSE" +
  ") AS distinct_users " +
  "JOIN user u ON u.user_id = distinct_users.user_id " +
  "JOIN streak s ON u.streak_id = s.streak_id " +
  "ORDER BY s.current_streak DESC " +
  "LIMIT :entryCount", nativeQuery = true)
  List<User> findTopFriendsByHighestCurrentStreak(@Param("userId") Long userId, @Param("entryCount") Integer entryCount);


  /**
   * Finds the X users surrounding a user by their total earned points.
   * @param userId The ID of the user you want to be surrounded.
   * @param entryCount The number of users above and below the user.
   * @return A list of users with a user with userId and 2X users surrounding it by their total earned points.
   */
  @Query(value = 
  // Define two CTEs (ranked_users and user_rank)
  // ranked_users virtual table that holds user info and their rank desc (1 is lowest)
  // user_rank virtual table that holds rank of the user with ID userId
  "WITH ranked_users AS ( " + 
  "SELECT u.*, RANK() OVER (ORDER BY p.total_earned_points DESC) AS user_rank " +
  "FROM user u  " +
  "JOIN point p ON u.point_id = p.point_id  " +
  "), user_rank AS (  " +
  "SELECT user_rank " +
  "FROM ranked_users  " +
  "WHERE user_id = :userId  ) " +
  // Get user attributes from ranked_users
  "SELECT ru.user_id, ru.created_at, ru.email, ru.first_name, ru.last_name, ru.password, ru.role,"
      + " ru.point_id, ru.streak_id, ru.checking_account_id, ru.savings_account_id, ru"
      + ".configuration_id, ru.profile_image, ru.subscription_level, ru.bankid_sub " +
  "FROM ranked_users ru, user_rank ur " +
  // Case handling for when user_rank is less than entryCount
  "WHERE ru.user_rank BETWEEN (CASE WHEN ur.user_rank > :entryCount THEN ur.user_rank - :entryCount ELSE 1 END) AND (ur.user_rank + :entryCount)",
  nativeQuery = true)
  List<User> findSurroundingUsersByTotalEarnedPoints(@Param("userId") Long userId, @Param("entryCount") Integer entryCount);
  
  @Query(value = 
  // Define two CTEs (ranked_users and user_rank)
  // ranked_users virtual table that holds user info and their rank desc (1 is lowest)
  // user_rank virtual table that holds rank of the user with ID userId
  "WITH ranked_users AS ( " + 
  "SELECT u.*, RANK() OVER (ORDER BY s.current_streak DESC) AS user_rank  " +
  "FROM user u  " +
  "JOIN streak s ON u.streak_id = s.streak_id " +
  "), user_rank AS (  " +
  "SELECT user_rank " +
  "FROM ranked_users " +
  "WHERE user_id = :userId ) " +
  // Get user attributes from ranked_users
  "SELECT ru.user_id, ru.created_at, ru.email, ru.first_name, ru.last_name, ru.password, ru.role,"
      + " ru.point_id, ru.streak_id, ru.checking_account_id, ru.savings_account_id, ru"
      + ".configuration_id, ru.profile_image, ru.subscription_level, ru.bankid_sub " +
  "FROM ranked_users ru, user_rank ur " +
  // Case handling for when user_rank is less than entryCount
  "WHERE ru.user_rank BETWEEN (CASE WHEN ur.user_rank > :entryCount THEN ur.user_rank - :entryCount ELSE 1 END) AND (ur.user_rank + :entryCount)",
  nativeQuery = true)
  List<User> findSurroundingUsersByHighestCurrentStreak (@Param("userId") Long userId, @Param("entryCount") Integer entryCount);

  @Query(value = 
  // Define two CTEs (ranked_users and user_rank)
  // ranked_users virtual table that holds user info and their rank desc (1 is lowest)
  // user_rank virtual table that holds rank of the user with ID userId
  "WITH ranked_users AS ( " + 
  "SELECT u.*, RANK() OVER (ORDER BY s.highest_streak DESC) AS user_rank " +
  "FROM user u " +
  "JOIN streak s ON u.streak_id = s.streak_id " +
  "), user_rank AS ( " +
  "SELECT user_rank " +
  "FROM ranked_users " +
  "WHERE user_id = :userId ) " +
  // Get user attributes from ranked_users
  "SELECT ru.user_id, ru.created_at, ru.email, ru.first_name, ru.last_name, ru.password, ru.role,"
      + " ru.point_id, ru.streak_id, ru.checking_account_id, ru.savings_account_id, ru"
      + ".configuration_id, ru.profile_image, ru.subscription_level, ru.bankid_sub " +
  "FROM ranked_users ru, user_rank ur " +
  // Case handling for when user_rank is less than entryCount
  "WHERE ru.user_rank BETWEEN (CASE WHEN ur.user_rank > :entryCount THEN ur.user_rank - :entryCount ELSE 1 END) AND (ur.user_rank + :entryCount)",
  nativeQuery = true)
  List<User> findSurroundingUsersByHighestEverStreak (@Param("userId") Long userId, @Param("entryCount") Integer entryCount); 

  @Query(value = 
            "WITH ranked_users AS (" +
            "    SELECT u.user_id, RANK() OVER (ORDER BY p.total_earned_points DESC) AS user_rank " +
            "    FROM user u " +
            "    JOIN point p ON u.point_id = p.point_id " +
            "), " +
            "user_rank AS (" +
            "    SELECT user_rank " +
            "    FROM ranked_users " +
            "    WHERE user_id = :userId " +
            ") " +
            "SELECT user_rank " +
            "FROM user_rank",
            nativeQuery = true)
    long findUserRankByTotalEarnedPoints(@Param("userId") Long userId);

    @Query(value = 
            "WITH ranked_users AS (" +
            "    SELECT u.user_id, RANK() OVER (ORDER BY s.current_streak DESC) AS user_rank " +
            "    FROM user u " +
            "    JOIN streak s ON u.streak_id = s.streak_id " +
            "), " +
            "user_rank AS (" +
            "    SELECT user_rank " +
            "    FROM ranked_users " +
            "    WHERE user_id = :userId " +
            ") " +
            "SELECT user_rank " +
            "FROM user_rank",
            nativeQuery = true)
    long findUserRankByCurrentStreak(@Param("userId") Long userId);

    @Query(value = 
            "WITH ranked_users AS (" +
            "    SELECT u.user_id, RANK() OVER (ORDER BY s.highest_streak DESC) AS user_rank " +
            "    FROM user u " +
            "    JOIN streak s ON u.streak_id = s.streak_id " +
            "), " +
            "user_rank AS (" +
            "    SELECT user_rank " +
            "    FROM ranked_users " +
            "    WHERE user_id = :userId " +
            ") " +
            "SELECT user_rank " +
            "FROM user_rank",
            nativeQuery = true)
    long findUserRankByHighestEverStreak(@Param("userId") Long userId);

    @Query("UPDATE User u SET u.savingsAccount = :account WHERE u = :user")
    void updateSavingsAccount(@Param("user") User user,@Param("account") Account account);

    @Query(value = "SELECT * FROM user u WHERE CONCAT(u.first_name, ' ', u.last_name) LIKE %:searchTerm%", nativeQuery = true)
    List<User> findUsersByName(@Param("searchTerm") String searchTerm);
    
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.subscriptionLevel = :subscriptionLevel WHERE u.id = :userId")
    void updateSubscriptionLevel(@Param("userId") Long userId, @Param("subscriptionLevel") SubscriptionLevel subscriptionLevel);

    @Query(value = "SELECT SUM(total_earned_points) FROM point", nativeQuery = true)
    long getSumTotalEarnedPoints();

    @Transactional
    @Modifying
    @Query(value = "UPDATE point p " +
                   "JOIN user u ON u.point_id = p.point_id " +
                   "SET p.current_points = p.current_points - :points " +
                   "WHERE u.user_id = :userId", nativeQuery = true)
    void deductPoints(@Param("userId") Long userId, @Param("points") int points);
}
