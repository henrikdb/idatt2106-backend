package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * The profile picture of a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile_picture")
public class ProfilePicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_picture_id")
	private Long id;

	@NonNull
	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "price", nullable = false)
	private int price;

}
