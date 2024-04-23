package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.exception.question.QuestionTypeNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.QuestionType;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Answer;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;
import no.ntnu.idi.stud.savingsapp.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;
    
    /**
     * Find all question objects present in the database.
     * 
     * @return A list of all question objects in the database.
     */
    @Override
    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }  
    
    /**
     * Find all answers in a question by its questionId
     * 
     * @param questionId The id of the question
     * @return A list of all the answer objects for the given questionId
     */
    @Override
    public List<Answer> findAnswersForQuestion(long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if (question != null) {
            return question.get().getAnswerList();
        } else {
            throw new IllegalArgumentException(); //TODO ADD CUSTOM EXCEPTION
        }
    }

    /**
     * Find a question based on the question type.
     * This is hardcoded based on the QuestionType and corresponding
     * questionId. 
     * 
     * @param type the type of question you want.
     * @return The question object for the given type.
     * @throws QuestionTypeNotFoundException if the given type is not found.

     */
    @Override
    public Question findQuestionByType(QuestionType type) {
        Optional<Question> question = null;
        switch(type) {
            case COMMITMENT:
                question = questionRepository.findById(1);
                break;
            case EXPERIENCE:
                question = questionRepository.findById(2);
                break;
            case CHALLENGE:
                question = questionRepository.findById(3);
                break;
        }   
        if (question != null) {
            return question.get();
        } else {
            throw new QuestionTypeNotFoundException(type.toString()); 
        }
    }
}
