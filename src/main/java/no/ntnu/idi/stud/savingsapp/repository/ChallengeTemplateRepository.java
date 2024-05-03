package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.goal.ChallengeTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link ChallengeType challengeType} entities.
 */
@Repository
public interface ChallengeTemplateRepository extends JpaRepository<ChallengeTemplate, Long> {

	/**
	 * Retrieves a list of {@link ChallengeTemplate challangeTempletes} by a list of
	 * {@link ChallengeType challengeType} enums.
	 * @param challengeType the list challengeType enums
	 * @return the list of challengeTemplates.
	 */
	List<ChallengeTemplate> findAllByChallengeTypeIn(List<ChallengeType> challengeType);

}
