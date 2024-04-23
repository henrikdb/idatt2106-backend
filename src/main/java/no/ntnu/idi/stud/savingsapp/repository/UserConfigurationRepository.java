package no.ntnu.idi.stud.savingsapp.repository;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.UserConfiguration;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.UserConfigurationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link UserConfiguration} entities.
 */
@Repository
public interface UserConfigurationRepository extends JpaRepository<UserConfiguration, UserConfigurationId> {

  /**
   * Finds all {@link UserConfiguration UserConfigurations} associated with one user.
   * @param userId The id of the user to search for.
   * @return The list of {@link UserConfiguration UserConfigurations}.
   */
  //@Query("SELECT uc.* FROM user_configuration uc WHERE uc.user_id = :userId")
  //List<UserConfiguration> findAllById_UserId(@Param("userId") Long userId);

}
