package no.ntnu.idi.stud.savingsapp.controller.question;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import no.ntnu.idi.stud.savingsapp.dto.user.ProfileDTO;
import no.ntnu.idi.stud.savingsapp.dto.question.QuestionDTO;
import no.ntnu.idi.stud.savingsapp.dto.user.UserUpdateDTO;
import no.ntnu.idi.stud.savingsapp.exception.user.PermissionDeniedException;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Answer;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;
import no.ntnu.idi.stud.savingsapp.security.AuthIdentity;
import no.ntnu.idi.stud.savingsapp.service.AnswerService;
import no.ntnu.idi.stud.savingsapp.service.QuestionService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import java.util.List;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<List<QuestionDTO>> getQuestions(
      @AuthenticationPrincipal AuthIdentity identity) {
    List<Question> questions = questionService.findAllQuestions();
    List<QuestionDTO> questionDTOs = new ArrayList<>();
    for(Question question : questions) { 
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
        questionDTOs.add(questionDTO);

        List<Answer> answers = answerService.findById(question.getId());

    }


    
  
}

package no.ntnu.idi.stud.savingsapp.repository;

public class AnswerRepository extends JpaRepository<Question, Long> {
    List<Question> findAllQuestions();
    
}
