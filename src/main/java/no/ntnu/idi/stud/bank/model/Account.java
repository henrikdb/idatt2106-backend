package no.ntnu.idi.stud.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import no.ntnu.idi.stud.bank.model.generated.RandomValue;

/**
 * Mock data for an account associated with a user in a bank.
 */
@Data
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bban")
  private Long bban;

  @Column(name = "balance")
  private Double balance;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "bank_profile_id")
  private BankProfile bankProfile;

  /**
   * Constructor for account.
   * Generate a random balance for the account when an instance is created.
   */
  public Account() {
    this.balance = RandomValue.generateAccountBalance();
  }
}
