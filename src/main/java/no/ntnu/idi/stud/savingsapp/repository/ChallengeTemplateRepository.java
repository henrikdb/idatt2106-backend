package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import no.ntnu.idi.stud.savingsapp.model.configuration.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.goal.ChallengeTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeTemplateRepository extends JpaRepository<ChallengeTemplate, Long> {
    
    List<ChallengeTemplate> findAllByChallengeType(ChallengeType challengeType);
}
