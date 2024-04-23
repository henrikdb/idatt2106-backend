package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.Commitment;
import no.ntnu.idi.stud.savingsapp.model.Experience;
import no.ntnu.idi.stud.savingsapp.model.bank.Account;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user in the system.
 * This class implements the UserDetails interface, providing the necessary information for
 * Spring Security to authenticate and authorize users and for use in testing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`user`") 
public class User implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NonNull
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NonNull
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NonNull
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "savings_account")
  private Account savingsAccount;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "savings_account")
  private Account checkingAccount;

  @NonNull
  @Column(name = "password", nullable = false)
  private String password;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Timestamp createdAt;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private List<SavingGoal> savingGoal;

  @ManyToMany
  @JoinTable(name = "badge_user",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "badge_id"))
  private List<Badge> badges;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private List<BadgeUser> badgeUserList;

  @OneToOne
  @JoinColumn(name = "point_id", nullable = true)
  private Point point;

  @OneToOne
  @JoinColumn(name = "streak_id", nullable = true)
  private Streak streak;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "commitment", nullable = false)
  private Commitment commitment;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "experience", nullable = false)
  private Experience experience;

  @ElementCollection
  @CollectionTable(name = "challenge_type", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private List<ChallengeType> challengeTypes;

  /**
   * Get the authorities granted to the user.
   *
   * @return A list of GrantedAuthority objects representing the authorities granted to the user.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  /**
   * Get a unique representation of the user.
   * This method uses the email as the username.
   *
   * @return The email of the email of the user.
   */
  @Override
  public String getUsername() {
    return this.email;
  }

  /**
   * Indicates whether the user's account has expired.
   *
   * @return true if the user's account is valid (i.e., non-expired), false otherwise.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is locked or unlocked.
   *
   * @return true if the user is not locked, false otherwise.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Indicates whether the user's credentials (password) has expired.
   *
   * @return true if the user's credentials are valid (i.e., non-expired), false otherwise.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is enabled or disabled.
   *
   * @return true if the user is enabled, false otherwise.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
