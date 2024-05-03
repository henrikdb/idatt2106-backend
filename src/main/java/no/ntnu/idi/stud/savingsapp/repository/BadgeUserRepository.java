package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.user.BadgeUser;
import no.ntnu.idi.stud.savingsapp.model.user.BadgeUserId; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeUserRepository extends JpaRepository<BadgeUser, BadgeUserId> { 

}
