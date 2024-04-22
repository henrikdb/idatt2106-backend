package no.ntnu.idi.stud.savingsapp.repository;

public class AnswerRepository extends JpaRepository<Question, Long> {
    List<Question> findAllQuestions();
    
}
