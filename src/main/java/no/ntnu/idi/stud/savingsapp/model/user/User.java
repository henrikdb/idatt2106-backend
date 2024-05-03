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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import no.ntnu.idi.stud.savingsapp.model.configuration.Configuration;
import no.ntnu.idi.stud.savingsapp.model.store.Item;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Represents a user in the system. This class implements the UserDetails interface,
 * providing the necessary information for Spring Security to authenticate and authorize
 * users and for use in testing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements UserDetails {

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
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "profile_image")
	private Long profileImage;

	@Column(name = "banner_image")
	private Long bannerImage;

	@Column(name = "checking_account_bban")
	private Long checkingAccountBBAN;

	@Column(name = "savings_account_bban")
	private Long savingsAccountBBAN;

	@NonNull
	@Column(name = "password")
	private String password;

	@Column(name = "bankid_sub", unique = true)
	private String bankIdSub;

	@NonNull
	@Column(name = "created_at", nullable = false)
	private Timestamp createdAt;

	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@ManyToMany
	@JoinTable(name = "badge_user", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "badge_id"))
	private List<Badge> badges;

	@ManyToMany
	@JoinTable(name = "inventory", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> items;

	@OneToOne
	@JoinColumn(name = "point_id")
	private Point point;

	@OneToOne
	@JoinColumn(name = "streak_id")
	private Streak streak;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "configuration_id")
	private Configuration configuration;

	@Enumerated(EnumType.STRING)
	@Column(name = "subscription_level", nullable = false)
	private SubscriptionLevel subscriptionLevel = SubscriptionLevel.DEFAULT;

	/**
	 * Get the authorities granted to the user.
	 * @return A list of GrantedAuthority objects representing the authorities granted to
	 * the user.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	/**
	 * Get a unique representation of the user. This method uses the email as the
	 * username.
	 * @return The email of the email of the user.
	 */
	@Override
	public String getUsername() {
		return this.email;
	}

	/**
	 * Indicates whether the user's account has expired.
	 * @return true if the user's account is valid (i.e., non-expired), false otherwise.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is locked or unlocked.
	 * @return true if the user is not locked, false otherwise.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired.
	 * @return true if the user's credentials are valid (i.e., non-expired), false
	 * otherwise.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is enabled or disabled.
	 * @return true if the user is enabled, false otherwise.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
