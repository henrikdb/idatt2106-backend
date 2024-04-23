package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.QuestionType;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Answer;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
    /**
     * Find all question objects present in the database.
     * 
     * @return A list of all question objects in the database.
     */
    List<Question> findAllQuestions();

    /**
     * Find all answers in a question by its questionId
     * 
     * @param questionId The id of the question
     * @return A list of all the answer objects for the given questionId
     */
    List<Answer> findAnswersForQuestion(long questionId);

    /**
     * Find a question based on the question type.
     * This is hardcoded based on the QuestionType and corresponding
     * questionId. 
     * 
     * @param type the type of question you want.
     * @return The question object for the given type.
     */
    Question findQuestionByType(QuestionType type);
}
