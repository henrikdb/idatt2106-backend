package no.ntnu.idi.stud.savingsapp.model.budget;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Represents an expense in the {@link Budget budget}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`expense`")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "expense_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "budget_id")
  private Budget budget;

  @NonNull
  @Column(name = "description", nullable = false)
  private String description;

  @NonNull
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;
}
