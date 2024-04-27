package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.model.BankAccountType;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.springframework.stereotype.Service;

/**
 * Service interface for user-related operations.
 */
@Service
public interface UserService {

	/**
	 * Authenticates a user with the provided email and password.
	 * @param email The email address of the user.
	 * @param password The password associated with the user's account.
	 * @return The authenticated user object if login is successful, or null otherwise.
	 */
	User login(String email, String password);

	/**
	 * Registers a new user.
	 * @param user The user object containing registration information.
	 * @return The registered user object.
	 */
	User register(User user);

	/**
	 * Updates the information of an existing user.
	 * @param user The User object containing updated information.
	 * @return The updated User object, persisted in the database.
	 */
	User update(User user);

	/**
	 * Retrieves a user by their email address.
	 * @param email The email address to search for in the user database.
	 * @return The User object associated with the specified email if found.
	 */
	User findByEmail(String email);

	/**
	 * Retrieves a user by their unique identifier.
	 * @param id The unique ID of the user.
	 * @return The User object associated with the specified ID if found.
	 */
	User findById(long id);

	/**
	 * Initiates the password reset process for a user identified by their email address.
	 * @param email The email address of the user requesting a password reset.
	 */
	void initiatePasswordReset(String email);

	/**
	 * Completes the password reset process by updating the user's password based on the
	 * provided reset token.
	 * @param token The password reset token that was sent to the user.
	 * @param password The new password to set for the user.
	 */
	void confirmPasswordReset(String token, String password);

	/**
	 * Select which bank account the user has selected as either savings- or checking
	 * account.
	 * @param bankAccountType The type of account, can either be a savings account or a
	 * checking account.
	 * @param bban The Basic Bank Account Number, specifying the account.
	 */
	Account selectBankAccount(BankAccountType bankAccountType, Long bban, Long userId);

}
