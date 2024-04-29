package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Service class for handling friend operations.
 */
@Service
public interface FriendService {
    /**
     * Retrieves all friends associated with a given user ID.
     *
     * @param userId The ID of the user whose friends are to be retrieved.
     * @return A list of Friend objects representing the user's friends.
     */
    List<Friend> getFriends(Long userId);
    
    /**
     * Retrieves all pending friend requests for a given user ID.
     *
     * @param userId The ID of the user whose friend requests are to be retrieved.
     * @return A list of Friend objects representing the friend requests.
     */
    List<Friend> getFriendRequests(Long userId);

    /**
     * Sends a friend request from one user to another.
     *
     * @param user The user sending the friend request.
     * @param friend The user to receive the friend request.
     */
    void addFriendRequest(User user, User friend);

    /**
     * Retrieves a specific friend request between two users.
     *
     * @param user The user who sent the friend request.
     * @param friend The user who received the friend request.
     * @return The Friend object if a request exists, otherwise null.
     */
    Friend getFriendRequest(User user, User friend);

    /**
     * Accepts a friend request, changing the attribute pending to false.
     *
     * @param friendRequest The friend request to be accepted.
     */
    void acceptFriendRequest(Friend friendRequest);

    /**
     * Get a Friend object if a relationship between the two users exists.
     *
     * @param user The first user.
     * @param friend The second user.
     * @return The Friend object representing the current status of their relationship.
     */
    Friend getFriendStatus(User user, User friend);

    /**
     * Deletes a friendship or a friend request between two users.
     *
     * @param friendStatus The friendship or friend request to be deleted.
     */
    void deleteFriendOrFriendRequest(Friend friendStatus);
}
