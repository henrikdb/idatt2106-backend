package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;
import no.ntnu.idi.stud.savingsapp.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;
    
    @Override
    public List<Question> findAllQuestions() {
        return questionRepository.findAllQuestions();
    }    
}
