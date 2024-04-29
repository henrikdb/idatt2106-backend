package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;

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

  @Query("SELECT f FROM Friend f WHERE f.id.friend.id = :userId OR f.id.user.id = :userId")
  List<Friend> findAllById_UserOrId_Friend(@Param("userId") Long userId);

  @Query("SELECT f FROM Friend f WHERE (f.id.friend.id = :userId OR f.id.user.id = :userId) AND f.pending = false")
  List<Friend> findAllById_UserOrId_FriendAndPendingFalse(@Param("userId") Long userId);

  @Query("SELECT f FROM Friend f WHERE f.id.friend.id = :userId AND f.pending = true")
  List<Friend> findAllById_FriendAndPendingTrue(@Param("userId") Long userId);

  @Transactional
  @Modifying
  @Query("UPDATE Friend f SET f.pending = false WHERE f.id = :friendId")
  void acceptFriendRequest(FriendId friendId);
}
