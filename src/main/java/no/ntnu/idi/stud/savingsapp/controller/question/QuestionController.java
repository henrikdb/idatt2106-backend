package no.ntnu.idi.stud.savingsapp.controller.question;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idi.stud.savingsapp.dto.question.AnswerDTO;
import no.ntnu.idi.stud.savingsapp.dto.question.QuestionDTO;
import no.ntnu.idi.stud.savingsapp.model.QuestionType;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Answer;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.Question;
import no.ntnu.idi.stud.savingsapp.service.QuestionService;
import no.ntnu.idi.stud.savingsapp.validation.Enumerator;

import java.util.List;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
@EnableAutoConfiguration
@Tag(name = "Question", description = "Retrieving question data")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ModelMapper modelMapper;


    // @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<List<QuestionDTO>> getQuestions(
    //     @AuthenticationPrincipal AuthIdentity identity) {
    //     List<Question> questions = questionService.findAllQuestions();
    //     List<QuestionDTO> questionDTOs = new ArrayList<>();

    //     for(Question question : questions) { // Loop through every question
    //         List<Answer> answers = questionService.findAnswersForQuestion(question.getId());
    //         List<AnswerDTO> answerDTOs = new ArrayList<>();
    //         for(Answer answer : answers) { // Loop through every answer for that
    //             AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);
    //             answerDTOs.add(answerDTO);
    //         }
    //         QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
    //         questionDTO.setAnswers(answerDTOs);
    //         questionDTOs.add(questionDTO);
    //     }
    //     return ResponseEntity.ok(questionDTOs);
    // }

    @Operation(
        summary = "Get question by type",
        description = "Get question that has been categorize with the given type"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved the question with the type provided.",
            content = {
                @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = QuestionDTO.class))
                )
            }
        )
    })
    @Parameters(value = {
        @Parameter(
            name = "type",
            description = "The type of question to be found",
            required = true,
            example = "EXPERIENCE"
        )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<QuestionDTO> getQuestion(
        @RequestParam @Enumerator(value = QuestionType.class, message = "Invalid type") String type) {
            
        Question question = questionService.findQuestionByType(QuestionType.valueOf(type));
        List<Answer> answers = questionService.findAnswersForQuestion(question.getId());
        List<AnswerDTO> answerDTOs = new ArrayList<>();
        for(Answer answer : answers) { 
            AnswerDTO answerDTO = modelMapper.map(answer, AnswerDTO.class);
            answerDTOs.add(answerDTO);
        }
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
        questionDTO.setAnswers(answerDTOs);

        return ResponseEntity.ok(questionDTO);
    }
}
