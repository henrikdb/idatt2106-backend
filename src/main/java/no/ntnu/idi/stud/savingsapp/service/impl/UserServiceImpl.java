package no.ntnu.idi.stud.savingsapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import no.ntnu.idi.stud.savingsapp.SparestiApplication;
import no.ntnu.idi.stud.savingsapp.bank.service.AccountService;
import no.ntnu.idi.stud.savingsapp.exception.auth.InvalidCredentialsException;
import no.ntnu.idi.stud.savingsapp.exception.user.EmailAlreadyExistsException;
import no.ntnu.idi.stud.savingsapp.exception.user.InvalidPasswordResetTokenException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserException;
import no.ntnu.idi.stud.savingsapp.exception.user.UserNotFoundException;
import no.ntnu.idi.stud.savingsapp.model.user.Feedback;
import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.PasswordResetToken;
import no.ntnu.idi.stud.savingsapp.model.user.Point;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.SearchFilter;
import no.ntnu.idi.stud.savingsapp.model.user.Streak;
import no.ntnu.idi.stud.savingsapp.model.user.SubscriptionLevel;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.FeedbackRepository;
import no.ntnu.idi.stud.savingsapp.repository.PasswordResetTokenRepository;
import no.ntnu.idi.stud.savingsapp.repository.PointRepository;
import no.ntnu.idi.stud.savingsapp.repository.StreakRepository;
import no.ntnu.idi.stud.savingsapp.repository.UserRepository;
import no.ntnu.idi.stud.savingsapp.service.FriendService;
import no.ntnu.idi.stud.savingsapp.service.UserService;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Collections;
import java.util.Iterator;

/**
 * Implementation of the UserService interface for user-related operations.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private static final Duration PASSWORD_RESET_DURATION = Duration.ofHours(1);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FriendService friendService;

  @Autowired
  private PointRepository pointRepository;

  @Autowired
  private StreakRepository streakRepository;

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
        log.error("[UserServiceImpl:login] invalid credentials: email: {}, password: {}", email, password);
        throw new InvalidCredentialsException();
      }
    } else {
      log.error("[UserServiceImpl:login] user is not found, email: {}", email);
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

    // Create and save a new Point object
    Point point = new Point();
    point.setCurrentPoints(0);
    point.setTotalEarnedPoints(0);
    point = pointRepository.save(point);

    user.setPoint(point);

    Streak streak = new Streak();
    streak.setCurrentStreak(0);
    streak.setCurrentStreakCreatedAt(Timestamp.from(Instant.now()));
    streak.setCurrentStreakUpdatedAt(Timestamp.from(Instant.now()));
    streak.setHighestStreak(0);
    streak.setHighestStreakCreatedAt(Timestamp.from(Instant.now()));
    streak.setHighestStreakEndedAt(Timestamp.from(Instant.now()));
    streak = streakRepository.save(streak);

    user.setStreak(streak);
    try {
      return userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      log.error("[UserServiceImpl:register] email already exists: {}", user.getEmail());
      throw new EmailAlreadyExistsException();
    }
  }

  /**
   * Authenticates a user by exchanging a BankID authorization code for an access token and retrieves user information.
   * This method contacts the BankID service to obtain an access token using the provided authorization code and then
   * fetches the user details from the BankID userinfo endpoint. If the user is new, it registers them in the database.
   *
   * @param code  The authorization code provided by the BankID authentication flow.
   * @param state The state parameter to ensure the response corresponds to the request made by the user.
   * @return A {@link User} object populated with details retrieved from BankID if authentication is successful,
   *         or null if an error occurs during the process.
   */
  @Override
  public User bankIDAuth(String code, String state) {
    try {
      String tokenUrl = "https://preprod.signicat.com/oidc/token";
      HttpPost httpPost = new HttpPost(tokenUrl);
      httpPost.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
      httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + "ZGVtby1wcmVwcm9kOm1xWi1fNzUtZjJ3TnNpUVRPTmI3T240YUFaN3pjMjE4bXJSVmsxb3VmYTg=");
      List<NameValuePair> params = new ArrayList<>();
      params.add(new BasicNameValuePair("client_id", "demo-preprod"));
      params.add(new BasicNameValuePair("redirect_uri", SparestiApplication.getBackendURL() + "/redirect"));
      params.add(new BasicNameValuePair("grant_type", "authorization_code"));
      params.add(new BasicNameValuePair("code", code));
      httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
      final String content = postRequest(httpPost);
      // parse response
      JSONObject jsonObject = new ObjectMapper().readValue(content, JSONObject.class);
      String accessToken = (String) jsonObject.get("access_token");

      //get userinfo
      HttpPost httpPost_userinfo = new HttpPost("https://preprod.signicat.com/oidc/userinfo");
      httpPost_userinfo.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
      httpPost_userinfo.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
      final String content_userinfo = postRequest(httpPost_userinfo);
      JSONObject jsonObject2 = new ObjectMapper().readValue(content_userinfo, JSONObject.class);

      String sub = (String) jsonObject2.get("sub");
      Optional<User> optionalUser = userRepository.findByBankIdSub(sub);
      if (optionalUser.isPresent()) {
        return optionalUser.get();
      }
      User user = new User();
      user.setRole(Role.USER);
      user.setCreatedAt(Timestamp.from(Instant.now()));
      user.setBankIdSub(sub);
      user.setFirstName((String) jsonObject2.get("given_name"));
      user.setLastName((String) jsonObject2.get("family_name"));
      userRepository.save(user);
      return user;
    } catch (Exception e) {
      log.error("[UserServiceImpl:bankIDAuth] an error occurred");
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Sends an HTTP POST request and returns the response content as a string.
   * This method is used internally to communicate with external services like BankID.
   *
   * @param httpPost The {@link HttpPost} object configured with the URL, headers, and body of the request.
   * @return A string containing the response body.
   * @throws Exception If the HTTP request fails or the server response indicates an error.
   */
  protected String postRequest(final HttpPost httpPost) throws Exception {
    try {
      CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().build();
      CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
      final int status = httpResponse.getStatusLine().getStatusCode();
      if (status == HttpStatus.SC_FORBIDDEN || status / 100 != 2) {
        log.error("[UserServiceImpl:postRequest] an error occurred");
        throw new Exception("Something went wrong!! Handle this properly!!!");
      }
      final String content = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
      return content;
    } catch (final Exception e) {
      log.error("[UserServiceImpl:postRequest] an error occurred");
      throw new Exception(e.getMessage());
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
      log.error("[UserServiceImpl:update] data integrity violation: {}", e.getMostSpecificCause().getMessage());
      throw new UserException(e.getMostSpecificCause().getMessage());
    }
  }

  /**
   * Deletes a user from the system based on the specified user ID.
   * This method permanently removes the user's record from the database. It should be used with caution,
   * as this operation is irreversible and results in the loss of all data associated with the user's account.
   *
   * @param userId The unique identifier of the user to be deleted.
   */
  @Override
  public void delete(long userId) {
    userRepository.deleteById(userId);
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
      log.error("[UserServiceImpl:updatePassword] invalid old password: {}", oldPassword);
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
      log.error("[UserServiceImpl:findByEmail] user is not found: {}", email);
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
      log.error("[UserServiceImpl:findById] user is not found: {}", userId);
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
      log.error("[UserServiceImpl:initiatePasswordReset] error generating token");
      throw new RuntimeException("Error generating token");
    }
    try {
      emailService.sendForgotPasswordEmail(email, token);
    } catch (MessagingException | IOException e) {
      log.error("[UserServiceImpl:initiatePasswordReset] an error occurred");
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
        log.error("[UserServiceImpl:confirmPasswordReset] invalid token: {}", token);
        throw new InvalidPasswordResetTokenException();
      }

      User user = resetToken.getUser();
      user.setPassword(passwordEncoder.encode(password));
      userRepository.save(user);
    } else {
      log.error("[UserServiceImpl:confirmPasswordReset] invalid token: {}", token);
      throw new InvalidPasswordResetTokenException();
    }
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
      List<User> friends = new ArrayList<>();
      switch (filter) {
          case NON_FRIENDS:
              friends = getFriends(userId);
              users.removeAll(friends);
              break;
          case FRIENDS:
              friends = getFriends(userId);
              Iterator<User> iterator = users.iterator();
              while (iterator.hasNext()) {
                  User user = iterator.next();
                  if (!friends.contains(user)) {
                      iterator.remove();
                  }
              }
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

  /**
   * Check if the user has more than or equal to
   * amount of current points as points
   * 
   * @param user the user
   * @param points the amount of points to compare with
   * @return true or false
   */
  @Override
  public Boolean hasMorePoints(User user, int points) {
    return user.getPoint().getCurrentPoints() >= points;
  }

  /**
   * Deduct a number of current points from the user
   *
   * @param userId The user
   * @param points The amount of current points to deduct
   */
  @Override
  public void deductPoints(Long userId, int points) {
    userRepository.deductPoints(userId, points);
  }
}
