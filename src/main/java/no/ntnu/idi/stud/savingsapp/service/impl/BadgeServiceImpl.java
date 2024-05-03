package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.exception.badge.BadgeNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Badge;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUser;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUserId;
import no.ntnu.idi.stud.savingsapp.repository.BadgeRepository;
import no.ntnu.idi.stud.savingsapp.repository.BadgeUserRepository;
import no.ntnu.idi.stud.savingsapp.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of BadgeService to manage badges.
 */
@Service
@Slf4j
public class BadgeServiceImpl implements BadgeService {

  @Autowired
  private BadgeRepository badgeRepository;

  @Autowired
  private BadgeUserRepository badgeUserRepository;

  /**
   * Retrieves a badge by its badge id.
   *
   * @param badgeId the badge id
   * @return the badge associated with its id.
   * @throws BadgeNotFoundException if the badge does not exist.
   */
  @Override
  public Badge findBadgeByBadgeId(Long badgeId) {
    Optional<Badge> optionalBadge = badgeRepository.findBadgeById(badgeId);
    if (optionalBadge.isPresent()) {
      return optionalBadge.get();
    } else {
      log.error("[BadgeServiceImpl:findBadgeByBadgeId] Badge is not found, id: {}", badgeId);
      throw new BadgeNotFoundException();
    }
  }

  /**
   * Retrieves a list of all badges.
   *
   * @return a list of all badges.
   */
  @Override
  public List<Badge> findAllBadges() {
    return badgeRepository.findAllBadges();
  }

  /**
   * Retrieves a list of all badges unlocked by a user.
   *
   * @param userId the id of the user
   * @return a list of badges.
   */
  @Override
  public List<Badge> findBadgesUnlockedByUser(Long userId) {
    return badgeRepository.findBadgesUnlockedByUserId(userId);
  }

  /**
   * Retrieves a list of all badges that are not unlocked by a user.
   *
   * @param userId the id of the user
   * @return a list of badges.
   */
  @Override
  public List<Badge> findBadgesNotUnlockedByUser(Long userId) {
    return badgeRepository.findBadgesNotUnlockedByUserId(userId);
  }

  /**
   * Retrieves a list of all badges that are not newly unlocked by a user.
   *
   * @param userId the id of the user
   * @return a list of newly unlocked badges.
   */
  @Override
  public List<Badge> findNewlyUnlockedBadgesByUserId(Long userId) {
    return badgeRepository.findNewlyUnlockedBadgesByUserId(userId);
  }

  /**
   * Adds a badge to a user badge inventory.
   *
   * @param badgeUserId the BadgeUserId reference.
   */
  @Override
  public void addBadgeToUser(BadgeUserId badgeUserId) {
    BadgeUser badgeUser = new BadgeUser(badgeUserId, Timestamp.from(Instant.now()));
    badgeUserRepository.save(badgeUser);
  }
}
