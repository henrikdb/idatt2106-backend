package no.ntnu.idi.stud.savingsapp.service;

import jakarta.mail.MessagingException;
import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.InvalidPasswordResetTokenException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.PasswordResetToken;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.PasswordResetTokenRepository;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the UserService interface for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  private static final Duration PASSWORD_RESET_DURATION = Duration.ofHours(1);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordResetTokenRepository tokenRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

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

  /**
   * Updates the details of an existing user in the database.
   *
   * @param user The user object containing updated fields that should be persisted.
   * @return The updated user object as persisted in the database.
   * @throws EmailAlreadyExistsException If an attempt to update the user data results in a violation of unique constraint for the email field.
   */
  @Override
  public User update(User user) {
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      throw new EmailAlreadyExistsException();
    }
  }

  /**
   * Retrieves a user by their email address.
   *
   * @param email The email address to search for in the database.
   * @return The user object associated with the specified email address.
   * @throws UserNotFoundException If no user is found associated with the provided email address.
   */
  @Override
  public User findByEmail(String email) {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param userId The unique ID of the user to retrieve.
   * @return The user object associated with the specified ID.
   * @throws UserNotFoundException If no user is found with the specified ID.
   */
  @Override
  public User findById(long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Initiates the password reset process by generating a reset token and sending an email.
   *
   * @param email The email of the user requesting a password reset.
   */
  @Override
  public void initiatePasswordReset(String email) {
    User user = findByEmail(email);
    PasswordResetToken resetToken = new PasswordResetToken();
    resetToken.setUser(user);
    String token = UUID.randomUUID().toString();
    resetToken.setToken(token);
    resetToken.setCreatedAt(Timestamp.from(Instant.now()));
    try {
      tokenRepository.save(resetToken);
    } catch (DataIntegrityViolationException e) {
      throw new RuntimeException("Error generating token");
    }
    try {
      emailService.sendForgotPasswordEmail(email, token);
    } catch (MessagingException | IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Confirms and processes the password reset request by updating the user's password.
   *
   * @param token The reset token provided by the user.
   * @param password The new password to be set for the user.
   * @throws InvalidPasswordResetTokenException If the token is expired or invalid.
   */
  @Override
  public void confirmPasswordReset(String token, String password) {
    Optional<PasswordResetToken> optionalResetToken = tokenRepository.findByToken(token);
    if (optionalResetToken.isPresent()) {
      PasswordResetToken resetToken = optionalResetToken.get();

      LocalDateTime tokenCreationDate = resetToken.getCreatedAt().toLocalDateTime();
      Duration durationBetween = Duration.between(tokenCreationDate, LocalDateTime.now());
      if (durationBetween.isNegative() || durationBetween.compareTo(PASSWORD_RESET_DURATION) > 0) {
        throw new InvalidPasswordResetTokenException();
      }

      User user = resetToken.getUser();
      user.setPassword(passwordEncoder.encode(password));
      userRepository.save(user);
    } else {
      throw new InvalidPasswordResetTokenException();
    }
  }
}
