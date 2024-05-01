package no.ntnu.idi.stud.savingsapp.service.impl;

import jakarta.mail.MessagingException;
import no.ntnu.idi.stud.savingsapp.bank.model.Account;
import no.ntnu.idi.stud.savingsapp.bank.service.AccountService;
import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.InvalidPasswordResetTokenException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.BankAccountType;
import no.ntnu.idi.stud.savingsapp.model.user.Feedback;
import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.PasswordResetToken;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.SearchFilter;
import no.ntnu.idi.stud.savingsapp.model.user.SubscriptionLevel;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.FeedbackRepository;
import no.ntnu.idi.stud.savingsapp.repository.PasswordResetTokenRepository;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;
import no.ntnu.idi.stud.savingsapp.service.EmailService;
import no.ntnu.idi.stud.savingsapp.service.FriendService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;

/**
 * Implementation of the UserService interface for user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

  private static final Duration PASSWORD_RESET_DURATION = Duration.ofHours(1);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FriendService friendService;

  @Autowired
  private PasswordResetTokenRepository tokenRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

  @Autowired
  AccountService accountService;

  @Autowired
  FeedbackRepository feedbackRepository;

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
   * Updates the password of a user.
   *
   * @param id The ID of the user
   * @param oldPassword The old password
   * @param newPassword The new password
   * @return The updated User object, persisted in the database.
   * @throws InvalidCredentialsException if the old password is invalid.
   */
  @Override
  public User updatePassword(long id, String oldPassword, String newPassword) {
    User user = findById(id);
    boolean match = passwordEncoder.matches(oldPassword, user.getPassword());
    if (match) {
      String encodedPassword = passwordEncoder.encode(newPassword);
      user.setPassword(encodedPassword);
    } else {
      throw new InvalidCredentialsException("Old password is invalid");
    }
    return userRepository.save(user);
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

  @Override
  public Account selectBankAccount(BankAccountType bankAccountType, Long bban, Long userId) {
    User user = findById(userId);
    Account account = accountService.getAccountByBban(bban);
    if (bankAccountType == BankAccountType.SAVING_ACCOUNT) {
      user.setSavingsAccount(account);
    }
    else if (bankAccountType == BankAccountType.CHECKING_ACCOUNT){
      user.setCheckingAccount(account);
    }else {
      throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Account type not supported");
    }
    update(user);
    return account;
  }

  /**
   * Retrieves a list of {@link User} objects representing the friends of the specified user.
   *
   * @param userId The ID of the user whose friends are to be retrieved
   * @return a list of {@link User} instances representing the user's friends
   */
  @Override
  public List<User> getFriends(Long userId) {
    List<Friend> friendsFriend = friendService.getFriends(userId);
    List<User> friendsUser = new ArrayList<>();

    for(Friend friend : friendsFriend) {
      if(friend.getId().getUser().getId() != userId) {
        friendsUser.add(friend.getId().getUser());
      } else {
        friendsUser.add(friend.getId().getFriend());
      }
    }
    return friendsUser;
  }

  /**
   * Retrieves a list of {@link User} objects representing the friend requests of the specified user.
   *
   * @param userId The ID of the user whose friend requests are to be retrieved
   * @return a list of {@link User} instances representing the user's friend requests
   */
  @Override
  public List<User> getFriendRequests(Long userId) {
    List<Friend> friendsFriend = friendService.getFriendRequests(userId);
    List<User> friendsUser = new ArrayList<>();

    for(Friend friend : friendsFriend) {
      if(friend.getId().getUser().getId() != userId) {
        friendsUser.add(friend.getId().getUser());
      } else {
        friendsUser.add(friend.getId().getFriend());
      }
    }
    return friendsUser;
  }

  /**
   * Retrieves a list of User entities based on a search term and a specified filter.
   *
   * @param userId The ID of the user. Used to exclude that user and all of its friends
   * from the result.
   * @param searchTerm The search term used to filter user names.
   * @param filter A filter that is used to filter based on a category.
   * @return A list of User objects that match the search criteria and filter.
   */
  @Override
  public List<User> getUsersByNameAndFilter(Long userId, String searchTerm, SearchFilter filter) {
      List<User> users = userRepository.findUsersByName(searchTerm);
      users.removeIf(user -> user.getId().equals(userId));
      switch (filter) {
          case NON_FRIENDS:
              List<User> friends = getFriends(userId);
              users.removeAll(friends);
              break;
      }
      return users;
  }

  /**
   * Retrieves a list of randomly selected {@link User} objects based on the specified filter.
   *
   * @param userId The ID of the user. Used to exclude that user and all of its friends
   * from the result depending on filter.
   * @param amount The number of random users to retrieve.
   * @param filter A filter that is used to filter based on a category.
   * @return A list of randomly selected {@link User} objects.
   */
  @Override
  public List<User> getRandomUsers(Long userId, int amount, SearchFilter filter) {
    List<User> users = userRepository.findAll();
      users.removeIf(user -> user.getId().equals(userId));
      switch (filter) {
          case NON_FRIENDS:
              List<User> friends = getFriends(userId);
              users.removeAll(friends);
              break;
      }
      
      Collections.shuffle(users);
      while(users.size() > amount) {
        users.remove(users.get(users.size() - 1));
      }
      return users;
  }


  /**
   * Updates the subscription level of a specified user.
   *
   * @param userId The ID of the user whose subscription level is to be updated.
   * @param subscriptionLevel The new SubscriptionLevel to assign to the user.
   */
  @Override
  public void updateSubscriptionLevel(Long userId, SubscriptionLevel subscriptionLevel) {
    userRepository.updateSubscriptionLevel(userId, subscriptionLevel);
  }
  /**
   * Sends feedback from an email.
   *
   * @param email The email.
   * @param message The message.
   */
  @Override
  public void sendFeedback(String email, String message) {
    Feedback feedback = Feedback.builder().email(email).message(message).createdAt(Timestamp.from(Instant.now())).build();
    feedbackRepository.save(feedback);
  }

  /**
   * Get all feedback.
   *
   * @return A list containing all feedback.
   */
  @Override
  public List<Feedback> getFeedback() {
    return feedbackRepository.findAll();
  }

  @Override
  public Boolean hasMorePoints(User user, int points) {
    return user.getPoint().getCurrentPoints() >= points;
  }
}
