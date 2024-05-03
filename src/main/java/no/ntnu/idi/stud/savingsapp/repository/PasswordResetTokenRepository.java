package no.ntnu.idi.stud.savingsapp.repository;

import no.ntnu.idi.stud.savingsapp.model.user.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link PasswordResetToken} entities.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	/**
	 * Retrieves a password reset token by its token string. This method is used to fetch
	 * a password reset token from the database to verify its validity and to perform
	 * operations like password reset confirmation.
	 * @param token The unique string of the password reset token.
	 * @return An Optional containing the found PasswordResetToken or an empty Optional if
	 * no token is found.
	 */
	Optional<PasswordResetToken> findByToken(String token);

}
