package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
   * Finds the top X users with the highest total earned points.
   * @param entryCount The maximum number of users to return.
   * @return A list of users sorted by total earned points in descending order.
   */
  @Query(value = "SELECT u.* FROM User u JOIN Point p ON u.point.id = p.id ORDER BY p.totalEarnedPoints DESC LIMIT :entryCount", nativeQuery = true)
  List<User> findTopUsersByTotalEarnedPoints(@Param("entryCount") Integer entryCount);

  /**
   * Finds the top X users with the highest ever streak.
   * @param entryCount The maximum number of users to return.
   * @return A list of users sorted by highest ever streak in descending order.
   */
  @Query(value = "SELECT u.* FROM User u JOIN Streak s ON u.streak.id = s.id ORDER BY s.highestStreak DESC LIMIT :entryCount", nativeQuery = true)
  List<User> findTopUsersByHighestEverStreak(@Param("entryCount") Integer entryCount);

  /**
   * Finds the top X users with the highest current streak.
   * @param entryCount The maximum number of users to return.
   * @return A list of users sorted by highest current streak in descending order.
   */
  @Query(value = "SELECT u.* FROM user u JOIN Streak s ON u.streak.id = s.id ORDER BY s.currentStreak DESC LIMIT :entryCount", nativeQuery = true)
  List<User> findTopUsersByHighestCurrentStreak(@Param("entryCount") Integer entryCount);
}
