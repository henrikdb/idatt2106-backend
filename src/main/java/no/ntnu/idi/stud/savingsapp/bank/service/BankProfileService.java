package no.ntnu.idi.stud.savingsapp.bank.service;

import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileDTO;
import no.ntnu.idi.stud.savingsapp.bank.dto.BankProfileResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface BankProfileService {

  BankProfileResponseDTO saveBankProfile(BankProfileDTO bankProfileDTO);
}
