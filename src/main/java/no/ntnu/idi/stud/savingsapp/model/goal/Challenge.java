package no.ntnu.idi.stud.savingsapp.model.goal;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a challenge need to achieve a {@link Goal}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "challenge")
public class Challenge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "challenge_id")
	private Long id;

	@Column(name = "potential_amount", nullable = false)
	private BigDecimal potentialAmount;

	@Column(name = "points", nullable = false)
	private int points;

	@Column(name = "check_days", nullable = false)
	private int checkDays;

	@Column(name = "total_days", nullable = false)
	private int totalDays;

	@NonNull
	@Column(name = "start_date", nullable = false)
	private Timestamp startDate;

	@NonNull
	@Column(name = "end_date", nullable = false)
	private Timestamp endDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "challenge_template_id")
	private ChallengeTemplate template;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "challenge_id")
	private List<Progress> progressList;

}
