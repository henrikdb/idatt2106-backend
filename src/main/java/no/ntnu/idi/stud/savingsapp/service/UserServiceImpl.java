package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the UserService interface for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  private final List<User> users = new ArrayList<>();

  /**
   * Authenticates a user with the provided email and password.
   *
   * @param email The email address of the user.
   * @param password The password associated with the user's account.
   * @return The authenticated user object if login is successful.
   * @throws InvalidCredentialsException if the provided credentials are invalid.
   * @throws UserNotFoundException if the user with the provided email is not found.
   */
  @Override
  public User login(String email, String password) {
    for (User user : users) {
      if (user.getEmail().equalsIgnoreCase(email)) {
        if (!user.getPassword().equalsIgnoreCase(password))
          throw new InvalidCredentialsException();
        return user;
      }
    }
    throw new UserNotFoundException();
  }

  /**
   * Registers a new user.
   *
   * @param user The user object containing registration information.
   * @return The registered user object.
   * @throws EmailAlreadyExistsException if the provided email already exists in the system.
   */
  @Override
  public User register(User user) {
    if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail())))
      throw new EmailAlreadyExistsException();
    user.setRole(Role.USER);
    users.add(user);
    return user;
  }
}
