package no.ntnu.idi.stud.savingsapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idi.stud.savingsapp.model.user.configuration.UserConfiguration;
import no.ntnu.idi.stud.savingsapp.repository.UserConfigurationRepository;
import no.ntnu.idi.stud.savingsapp.service.UserConfigurationService;
import org.springframework.stereotype.Service;

@Service
public class UserConfigurationServiceImpl implements UserConfigurationService {

  private UserConfigurationRepository userConfigRepository;

  /**
   * Gets a list containing a list of user configurations.
   * This method lets sorts the configurations based on what question they are in relation to.
   *
   * @return A list containing a list of user configurations.
   */
  /*public List<List<UserConfiguration>> getSortedUserConfigs(Long userId) {
    List<UserConfiguration> suitableChallenges = new ArrayList<>();
    List<UserConfiguration> willingnessToChange = new ArrayList<>();
    List<UserConfiguration> experience = new ArrayList<>();

    List<UserConfiguration> userConfigurations = userConfigRepository.findAllByUserId(userId);

    for (UserConfiguration userConfig : userConfigurations) {
      // suitable challenges should be first, since there are possibly hundreds of these
      switch (userConfig.getId().getQuestion().getQuestionText()) {
        case "suitable challenges" -> suitableChallenges.add(userConfig);
        case "willingness to change" -> willingnessToChange.add(userConfig);
        case "experience" -> experience.add(userConfig);
      }
    }
    List<List<UserConfiguration>> sortedUserConfigs = new ArrayList<>();
    sortedUserConfigs.add(suitableChallenges);
    sortedUserConfigs.add(willingnessToChange);
    sortedUserConfigs.add(experience);

    return sortedUserConfigs;
  }*/
}
