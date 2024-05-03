package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.user.Streak;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {

}
