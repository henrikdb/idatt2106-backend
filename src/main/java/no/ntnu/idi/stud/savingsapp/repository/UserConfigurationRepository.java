package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.user.configuration.UserConfiguration;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.UserConfigurationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link UserConfiguration} entities.
 */
@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, UserConfigurationId> {

}
