package no.ntnu.idi.stud.savingsapp.bank.service;

import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileResponseDTO;
import org.springframework.stereotype.Service;

/**
 * Service interface for bank profile related operations.
 */
@Service
public interface BankProfileService {

  /**
   * Create a new bank profile.
   *
   * @param bankProfileDTO The DTO containing the user's Social Security Number.
   * @return a {@link BankProfileResponseDTO} containing profile information.
   */
  BankProfileResponseDTO saveBankProfile(BankProfileDTO bankProfileDTO);
}
