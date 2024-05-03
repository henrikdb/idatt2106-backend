package no.ntnu.idi.stud.savingsapp.service.impl;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.FriendId;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.FriendRepository;
import no.ntnu.idi.stud.savingsapp.service.FriendService;

/**
 * Implementation of the FriendService interface for handling friend operations.
 */
@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendRepository friendRepository;

	/**
	 * Retrieves all friends associated with a given user ID.
	 * @param userId The ID of the user whose friends are to be retrieved.
	 * @return A list of Friend objects representing the user's friends.
	 */
	@Override
	public List<Friend> getFriends(Long userId) {
		return friendRepository.findAllById_UserOrId_FriendAndPendingFalse(userId);
	}

	/**
	 * Retrieves all pending friend requests for a given user ID.
	 * @param userId The ID of the user whose friend requests are to be retrieved.
	 * @return A list of Friend objects representing the friend requests.
	 */
	@Override
	public List<Friend> getFriendRequests(Long userId) {
		return friendRepository.findAllById_FriendAndPendingTrue(userId);
	}

	/**
	 * Sends a friend request from one user to another.
	 * @param user The user sending the friend request.
	 * @param friend The user to receive the friend request.
	 */
	@Override
	public void addFriendRequest(User user, User friend) {
		FriendId friendId = new FriendId();
		friendId.setFriend(friend);
		friendId.setUser(user);
		friendRepository.save(new Friend(friendId, true, new Timestamp(System.currentTimeMillis())));
	}

	/**
	 * Get a Friend object if a relationship between the two users exists.
	 * @param user The first user.
	 * @param friend The second user.
	 * @return The Friend object representing the current status of their relationship.
	 */
	@Override
	public Friend getFriendStatus(User user, User friend) {
		// Attempt to find the friend entry where the user initiated the friendship
		FriendId friendId = new FriendId();
		friendId.setFriend(friend);
		friendId.setUser(user);
		Optional<Friend> friendStatus = friendRepository.findById(friendId);

		// If not found, try the reverse where the friend initiated the friendship
		if (!friendStatus.isPresent()) {
			friendId.setFriend(user);
			friendId.setUser(friend);
			friendStatus = friendRepository.findById(friendId);
		}

		// Return the friend entry if present, or null if no relationship exists
		return friendStatus.orElse(null);
	}

	/**
	 * Retrieves a specific friend request between two users.
	 * @param user The user who sent the friend request.
	 * @param friend The user who received the friend request.
	 * @return The Friend object if a request exists, otherwise null.
	 */
	@Override
	public Friend getFriendRequest(User user, User friend) {
		Friend friendRequest = getFriendStatus(user, friend);
		if (friendRequest.isPending() == true && friendRequest.getId().getUser().getId().equals(friend.getId())) {
			return friendRequest;
		}
		else {
			return null;
		}
	}

	/**
	 * Accepts a friend request, changing the attribute pending to false.
	 * @param friendRequest The friend request to be accepted.
	 */
	@Override
	public void acceptFriendRequest(Friend friendRequest) {
		friendRepository.acceptFriendRequest(friendRequest.getId());
	}

	/**
	 * Deletes a friendship or a friend request between two users.
	 * @param friendStatus The friendship or friend request to be deleted.
	 */
	@Override
	public void deleteFriendOrFriendRequest(Friend friendStatus) {
		friendRepository.delete(friendStatus);
	}

}
