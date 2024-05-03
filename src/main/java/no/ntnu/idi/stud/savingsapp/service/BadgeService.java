package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.user.Badge;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUserId;
import org.springframework.stereotype.Service;

/**
 * Service interface for badge-related operations
 */
@Service
public interface BadgeService {

  /**
   * Retrieves a badge by its badge id.
   *
   * @param badgeId the badge id
   * @return the badge associated with its id.
   */
  Badge findBadgeByBadgeId(Long badgeId);

  /**
   * Retrieves a list of all badges.
   *
   * @return a list of all badges.
   */
  List<Badge> findAllBadges();

  /**
   * Retrieves a list of all badges unlocked by a user.
   *
   * @param userId the id of the user
   * @return a list of badges.
   */
  List<Badge> findBadgesUnlockedByUser(Long userId);

  /**
   * Retrieves a list of all badges that are not unlocked by a user.
   *
   * @param userId the id of the user
   * @return a list of badges.
   */
  List<Badge> findBadgesNotUnlockedByUser(Long userId);

  /**
   * Retrieves a list of all badges that are not newly unlocked by a user.
   *
   * @param userId the id of the user
   * @return a list of newly unlocked badges.
   */
  List<Badge> findNewlyUnlockedBadgesByUserId(Long userId);

  /**
   * Adds a badge to a user badge inventory.
   *
   * @param badgeUserId the BadgeUserId reference.
   */
  void addBadgeToUser(BadgeUserId badgeUserId);

}