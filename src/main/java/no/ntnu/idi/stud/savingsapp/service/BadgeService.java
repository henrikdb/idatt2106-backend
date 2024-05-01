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

  Badge findBadgeByBadgeId(Long badgeId);

  List<Badge> findAllBadges();

  List<Badge> findBadgesUnlockedByUser(Long userId);

  List<Badge> findBadgesNotUnlockedByUser(Long userId);

  List<Badge> findNewlyUnlockedBadgesByUserId(Long userId);

  void addBadgeToUser(BadgeUserId badgeUserId);

}