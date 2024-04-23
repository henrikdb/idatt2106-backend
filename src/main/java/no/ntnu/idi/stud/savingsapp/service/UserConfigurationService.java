package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.UserConfiguration;
import org.springframework.stereotype.Service;

/**
 * Service interface for UserConfig related operations.
 */
@Service
public interface UserConfigurationService {

  /**
   * Gets a list containing a list of user configurations.
   * This method lets sorts the configurations based on what question they are in relation to.
   *
   * @return A list containing a list of user configurations.
   */
  public List<List<UserConfiguration>> getSortedUserConfigs(Long userId);

}
