package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.bank.Account;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingGoal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
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
  @Column(name = "email", nullable = false)
  private String email;

  @OneToOne
  @Column(name = "savings_account", nullable = false, unique = true)
  private Account savingsAccount;

  @OneToOne
  @Column(name = "checking_account", nullable = false, unique = true)
  private Account checkingAccount;

  @NonNull
  @Column(name = "password", nullable = false)
  private String password;

  @NonNull
  @Column(name = "created_at", nullable = false)
  private Date createdAt;

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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<BadgeUser> badgeUserList;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
