package no.ntnu.idi.stud.savingsapp.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import no.ntnu.idi.stud.savingsapp.exception.badge.BadgeNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Badge;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUser;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUserId;
import no.ntnu.idi.stud.savingsapp.repository.BadgeRepository;
import no.ntnu.idi.stud.savingsapp.repository.BadgeUserRepository;
import no.ntnu.idi.stud.savingsapp.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgeServiceImpl implements BadgeService {

  @Autowired
  private BadgeRepository badgeRepository;

  @Autowired
  private BadgeUserRepository badgeUserRepository;

  @Override
  public Badge findBadgeByBadgeId(Long badgeId) {
    Optional<Badge> optionalBadge = badgeRepository.findBadgeById(badgeId);
    if (optionalBadge.isPresent()) {
      return optionalBadge.get();
    } else {
      throw new BadgeNotFoundException();
    }
  }

  @Override
  public List<Badge> findAllBadges() {
    return badgeRepository.findAllBadges();
  }

  @Override
  public List<Badge> findBadgesUnlockedByUser(Long userId) {
    return badgeRepository.findBadgesUnlockedByUserId(userId);
  }

  @Override
  public List<Badge> findBadgesNotUnlockedByUser(Long userId) {
    return badgeRepository.findBadgesNotUnlockedByUserId(userId);
  }

  @Override
  public List<Badge> findNewlyUnlockedBadgesByUserId(Long userId) {
    return badgeRepository.findNewlyUnlockedBadgesByUserId(userId);
  }

  @Override
  public void addBadgeToUser(BadgeUserId badgeUserId) {
    BadgeUser badgeUser = new BadgeUser(badgeUserId, Timestamp.from(Instant.now()));
    badgeUserRepository.save(badgeUser);
  }
}
