package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;
import java.util.List;

public interface QuestionService {
    List<Question> findAllQuestions();
}
