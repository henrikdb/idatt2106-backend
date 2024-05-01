package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.model.BankAccountType;
import no.ntnu.idi.stud.savingsapp.model.user.Feedback;
import no.ntnu.idi.stud.savingsapp.model.user.SearchFilter;
import no.ntnu.idi.stud.savingsapp.model.user.SubscriptionLevel;
import no.ntnu.idi.stud.savingsapp.model.user.User;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service interface for user-related operations.
 */
@Service
public interface UserService {

  /**
   * Authenticates a user with the provided email and password.
   *
   * @param email The email address of the user.
   * @param password The password associated with the user's account.
   * @return The authenticated user object if login is successful, or null otherwise.
   */
  User login(String email, String password);

  /**
   * Registers a new user.
   *
   * @param user The user object containing registration information.
   * @return The registered user object.
   */
  User register(User user);

  /**
   * Authenticates a user using the BankID authentication mechanism.
   * This method processes the authentication by using a unique code and state received from the BankID service.
   *
   * @param code  The unique authorization code received from BankID after the user's successful authorization.
   *              This code is used to request the access token from BankID servers.
   * @param state The state parameter initially sent by the application to BankID to prevent CSRF attacks.
   *              This should match the state stored in the session or a similar safe place to ensure that
   *              the response corresponds to the user's request.
   * @return A User object representing the authenticated user. This object includes user-specific data
   *         such as user ID, profile details, and roles, if authentication is successful.
   */
  User bankIDAuth(String code, String state);

  /**
   * Updates the information of an existing user.
   *
   * @param user The User object containing updated information.
   * @return The updated User object, persisted in the database.
   */
  User update(User user);

  /**
   * Updates the password of a user.
   *
   * @param id The ID of the user
   * @param oldPassword The old password
   * @param newPassword The new password
   * @return The updated User object, persisted in the database.
   */
  User updatePassword(long id, String oldPassword, String newPassword);

  /**
   * Retrieves a user by their email address.
   *
   * @param email The email address to search for in the user database.
   * @return The User object associated with the specified email if found.
   */
  User findByEmail(String email);

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id The unique ID of the user.
   * @return The User object associated with the specified ID if found.
   */
  User findById(long id);

  /**
   * Initiates the password reset process for a user identified by their email address.
   *
   * @param email The email address of the user requesting a password reset.
   */
  void initiatePasswordReset(String email);

  /**
   * Completes the password reset process by updating the user's password based on the provided reset token.
   *
   * @param token The password reset token that was sent to the user.
   * @param password The new password to set for the user.
   */
  void confirmPasswordReset(String token, String password);

  /**
   * Select which bank account the user has selected as either savings- or checking account.
   *
   * @param bankAccountType The type of account, can either be a savings account or a checking
   *                        account.
   * @param bban            The Basic Bank Account Number, specifying the account.
   */
  Account selectBankAccount(BankAccountType bankAccountType, Long bban, Long userId);

  /**
   * Retrieves a list of {@link User} objects representing the friends of the specified user.
   *
   * @param userId The ID of the user whose friends are to be retrieved
   * @return a list of {@link User} instances representing the user's friends
   */
  List<User> getFriends(Long userId);

  /**
   * Retrieves a list of {@link User} objects representing the friend requests of the specified user.
   *
   * @param userId The ID of the user whose friend requests are to be retrieved
   * @return a list of {@link User} instances representing the user's friend requests
   */
  List<User> getFriendRequests(Long userId);

  /**
   * Retrieves a list of User entities based on a search term and a specified filter.
   *
   * @param userId The ID of the user. Used to exclude that user and all of its friends
   * from the result depending on filter.
   * @param searchTerm The search term used to filter user names. 
   * @param filter A filter that is used to filter based on a category.
   * @return A list of User objects that match the search criteria and filter.
   */
  List<User> getUsersByNameAndFilter(Long userId, String searchTerm, SearchFilter filter);

  /**
   * Retrieves a list of randomly selected {@link User} objects based on the specified filter.
   *
   * @param userId The ID of the user. Used to exclude that user and all of its friends
   * from the result depending on filter.
   * @param amount The number of random users to retrieve.
   * @param filter A filter that is used to filter based on a category.
   * @return A list of randomly selected {@link User} objects.
   */
  List<User> getRandomUsers(Long userId, int amount, SearchFilter filter);
  /**
   * Updates the subscription level of a specified user.
   *
   * @param userId The ID of the user whose subscription level is to be updated.
   * @param subscriptionLevel The new SubscriptionLevel to assign to the user.
   */
  void updateSubscriptionLevel(Long userId, SubscriptionLevel subscriptionLevel);

  /**
   * Sends feedback from an email.
   *
   * @param email   The email.
   * @param message The message.
   */
  void sendFeedback(String email, String message);

  /**
   * Get all feedback.
   *
   * @return A list containing all feedback.
   */
  List<Feedback> getFeedback();

  /**
   * Check if the user has more than or equal to
   * amount of current points as points
   * 
   * @param user the user
   * @param points the amount of points to compare with
   * @return true or false
   */
  Boolean hasMorePoints(User user, int points);

  /**
   * Deduct a number of current points from the user
   *
   * @param userId The user
   * @param points The amount of current points to deduct
   */
  void deductPoints(Long userId, int points);
}