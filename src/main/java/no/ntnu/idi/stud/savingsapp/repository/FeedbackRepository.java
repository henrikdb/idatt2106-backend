package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.user.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Feedback} entities.
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
