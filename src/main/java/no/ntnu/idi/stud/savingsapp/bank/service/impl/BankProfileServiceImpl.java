package no.ntnu.idi.stud.savingsapp.bank.service.impl;

import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.BankProfile;
import no.ntnu.idi.stud.savingsapp.bank.repository.BankProfileRepository;
import no.ntnu.idi.stud.savingsapp.bank.service.BankProfileService;
import no.ntnu.idi.stud.savingsapp.security.AuthorizationFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BankProfileServiceImpl implements BankProfileService {

  @Autowired
  private BankProfileRepository bankProfileRepository;
  private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);


  @Override
  public BankProfileResponseDTO saveBankProfile(BankProfileDTO bankProfileDTO) {
    BankProfile newBankProfile = new BankProfile();

    BankProfileResponseDTO savedProfileResponse = new BankProfileResponseDTO();
    LOGGER.info("request profile ssn: {}", bankProfileDTO.getSsn());

    newBankProfile.setSsn(bankProfileDTO.getSsn());
    LOGGER.info("new profile ssn: {}", newBankProfile.getSsn());
    try {
      BankProfile savedBankProfile = bankProfileRepository.save(newBankProfile);
      savedProfileResponse.setSsn(savedBankProfile.getSsn());
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatusCode.valueOf(400),
          "Could not create bank profile");
    }
    return savedProfileResponse;
  }
}
