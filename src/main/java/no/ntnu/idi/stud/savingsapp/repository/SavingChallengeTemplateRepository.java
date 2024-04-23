package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import no.ntnu.idi.stud.savingsapp.model.ChallengeType;
import no.ntnu.idi.stud.savingsapp.model.savings.SavingChallengeTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingChallengeTemplateRepository extends JpaRepository<SavingChallengeTemplate, Long> {
    
    List<SavingChallengeTemplate> findAllByChallengeType(ChallengeType challengeType);
}
