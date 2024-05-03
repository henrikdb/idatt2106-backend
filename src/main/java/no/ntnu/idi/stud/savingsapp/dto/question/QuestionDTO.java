package no.ntnu.idi.stud.savingsapp.dto.question;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDTO {

    String questionText;
    List<AnswerDTO> answers;

}
