package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for {@link Question} entities.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    /**
     * Retrieve all questions from the database.
     *
     * @return A list of all questions.
     */
    List<Question> findAll();

    /**
     * Retrieve a question by its unique identifier.
     *
     * @param questionId The ID of the question to retrieve.
     * @return An Optional containing the question, or empty if no question with the given ID exists.
     */
    Optional<Question> findById(long questionId);
    
}
