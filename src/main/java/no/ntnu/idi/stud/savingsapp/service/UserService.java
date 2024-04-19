package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.model.user.User;
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

  User update(User user);

  User findByEmail(String email);

  User findById(long id);
}
