package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.user.configuration.Answer;
import java.util.List;

public interface AnswerService {
    List<Answer> findById(long id);
}
