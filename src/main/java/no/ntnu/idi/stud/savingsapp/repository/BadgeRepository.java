package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.model.user.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

  /**
   * Returns a {@link Badge} by its id.
   *
   * @param badgeId The id of the badge
   * @return Badge
   */
  Optional<Badge> findBadgeById(long badgeId);

  @Query(value =
  "SELECT b.* FROM badge b;"
  , nativeQuery = true)
  List<Badge> findAllBadges();

  @Query(value =
  "SELECT b.* " +
  "FROM badge b " +
  "JOIN badge_user bu ON bu.badge_id = b.badge_id " +
  "WHERE bu.user_id = :userId"
  , nativeQuery = true)
  List<Badge> findBadgesUnlockedByUserId(@Param("userId") long userId);

  @Query(value =
  "SELECT b.* " +
  "FROM badge b " +
  "WHERE NOT EXISTS (" +
  "  SELECT 1 FROM badge_user bu " +
  "  WHERE bu.badge_id = b.badge_id AND bu.user_id = :userId" +
  ")"
  , nativeQuery = true)
  List<Badge> findBadgesNotUnlockedByUserId(@Param("userId") long userId);

  @Query(value =
  "SELECT b.* " +
  "FROM badge b, user u, point p " +
  "WHERE u.user_id = :userId " +
    "AND u.point_id = p.point_id " +
    "AND p.total_earned_points >= b.criteria " +
    "AND NOT EXISTS (" +
      "SELECT 1 " +
      "FROM badge_user bu " +
      "WHERE bu.badge_id = b.badge_id " +
      "AND bu.user_id = u.user_id" +
      ")"
  , nativeQuery = true)
  List<Badge> findNewlyUnlockedBadgesByUserId(@Param("userId") long userId);
}