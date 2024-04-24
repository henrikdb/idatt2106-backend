package no.ntnu.idi.stud.savingsapp.model.budget;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

  @NonNull
  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @OneToMany
  @JoinColumn(name = "expenses")
  private List<Expense> expenseList;
}
