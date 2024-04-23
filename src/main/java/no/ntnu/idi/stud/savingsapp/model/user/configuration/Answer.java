package no.ntnu.idi.stud.savingsapp.model.user.configuration;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import jakarta.persistence.JoinColumn;


/**
 * Represents an answer associated with a question.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long id;

  @NonNull
  @Column(name = "answer_text", nullable = false)
  private String answer_text;
}
