package no.ntnu.idi.stud.savingsapp.bank.service.impl;

import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileResponseDTO;
import no.ntnu.idi.stud.savingsapp.bank.model.BankProfile;
import no.ntnu.idi.stud.savingsapp.bank.repository.BankProfileRepository;
import no.ntnu.idi.stud.savingsapp.bank.service.BankProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementation of the {@link BankProfileService} interface for bank profile related operations.
 */
@Service
public class BankProfileServiceImpl implements BankProfileService {

  @Autowired
  private BankProfileRepository bankProfileRepository;

  /**
   * Create a new bank profile.
   *
   * @param bankProfileDTO The DTO containing the user's Social Security Number.
   * @return a {@link BankProfileResponseDTO} containing profile information.
   */
  @Override
  public BankProfileResponseDTO saveBankProfile(BankProfileDTO bankProfileDTO) {
    BankProfile newBankProfile = new BankProfile();
    BankProfileResponseDTO savedProfileResponse = new BankProfileResponseDTO();
    newBankProfile.setSsn(bankProfileDTO.getSsn());
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
