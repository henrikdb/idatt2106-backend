package no.ntnu.idi.stud.savingsapp.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents the points associated with a user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point")
public class Point {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_id")
	private Long id;

	@NonNull
	@Column(name = "current_points", nullable = false)
	private int currentPoints;

	@NonNull
	@Column(name = "total_earned_points", nullable = false)
	private int totalEarnedPoints;

}
