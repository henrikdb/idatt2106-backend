package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.boot.model.naming.IllegalIdentifierException;

import no.ntnu.idi.stud.savingsapp.model.user.configuration.Answer;

public class AnswerServiceImpl implements AnswerService {
    
    @Override
    public List<Answer> findById(long questionId) {
    }
}
