package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

/**
 * Repository interface for {@link Friend} entities.
 */
public interface FriendRepository extends JpaRepository<Friend, FriendId> {

  /**
   * Retrieves a list of all friends by user id or friend id.
   *
   * @param userId the user/friend identifier
   * @return the list of all friends by user id or friend id.
   */
  @Query("SELECT f FROM Friend f WHERE f.id.friend.id = :userId OR f.id.user.id = :userId")
  List<Friend> findAllById_UserOrId_Friend(@Param("userId") Long userId);

  /**
   * Retrieves a list of all friends by user id or friend id where pending is false.
   *
   * @param userId the user/friend identifier
   * @return the list of all friends by user id or friend id.
   */
  @Query("SELECT f FROM Friend f WHERE (f.id.friend.id = :userId OR f.id.user.id = :userId) AND f.pending = false")
  List<Friend> findAllById_UserOrId_FriendAndPendingFalse(@Param("userId") Long userId);

  /**
   * Retrieves a list of all friends by friend id where pending is true.
   *
   * @param userId the user/friend identifier
   * @return the list of all friends by friend id.
   */
  @Query("SELECT f FROM Friend f WHERE f.id.friend.id = :userId AND f.pending = true")
  List<Friend> findAllById_FriendAndPendingTrue(@Param("userId") Long userId);

  /**
   * Accepts a friend request by its friend id.
   *
   * @param friendId the id to the friend.
   */
  @Transactional
  @Modifying
  @Query("UPDATE Friend f SET f.pending = false WHERE f.id = :friendId")
  void acceptFriendRequest(FriendId friendId);
}
