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

  @Query("SELECT CASE WHEN fl1.id.user = :userId THEN fl1.id.friend ELSE fl1.id.user END "
      + "FROM FriendList fl1 "
      + "WHERE (fl1.id.user = :userId OR fl1.id.friend = :userId) AND fl1.pending = false")
  List<Long> findFriendIdsByUserId(@Param("userId") Long userId);
}
