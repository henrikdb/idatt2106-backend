package no.ntnu.idi.stud.savingsapp.bank.model;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "transaction_id")
  private Long id;

  @Column(name = "amount")
  private BigDecimal amount;

  @ManyToOne()
  @JoinColumn(name = "debtor_account_bban")
  private Account debtorAccount;

  @ManyToOne()
  @JoinColumn(name = "creditor_account_bban")
  private Account creditorAccount;

  @Column(name = "created_at")
  private Timestamp createdAt;

}
