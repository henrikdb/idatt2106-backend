package no.ntnu.idi.stud.savingsapp.service;

import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

/**
 * Implementation of the UserService interface for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

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
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      boolean match = passwordEncoder.matches(password, user.getPassword());
      if (match) {
        return user;
      } else {
        throw new InvalidCredentialsException();
      }
    } else {
      throw new UserNotFoundException();
    }
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
    String encodedPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    user.setRole(Role.USER);
    user.setCreatedAt(Timestamp.from(Instant.now()));
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new EmailAlreadyExistsException();
    }
  }

  @Override
  public User update(User user) {
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new EmailAlreadyExistsException();
    }
  }

  @Override
  public User findByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  @Override
  public User findById(long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UserNotFoundException();
    }
  }
}
