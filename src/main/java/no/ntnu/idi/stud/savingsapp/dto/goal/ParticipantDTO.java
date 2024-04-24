package no.ntnu.idi.stud.savingsapp.dto.goal;

import lombok.Data;
import no.ntnu.idi.stud.savingsapp.model.goal.participant.ParticipantRole;

@Data
public final class ParticipantDTO {

  private ParticipantRole role;

  private ParticipantUserDTO user;
}
