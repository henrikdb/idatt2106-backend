package no.ntnu.idi.stud.savingsapp.model.budget;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Represents a budget for a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`budget`")
public class Budget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "budget_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @NonNull
  @Column(name = "budget_name", nullable = false)
  private String budgetName;

  @NonNull
  @Column(name = "budget_amount", nullable = false)
  private BigDecimal budgetAmount;

  @NonNull
  @Column(name = "expense_amount", nullable = false)
  private BigDecimal expenseAmount;
}
