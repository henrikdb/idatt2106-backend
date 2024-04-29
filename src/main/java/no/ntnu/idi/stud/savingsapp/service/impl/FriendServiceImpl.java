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
     * Get a List of Friend objects where either the
     * user_id or friend_id is equal to userId and
     * pending is false.
     * 
     * @param userId The id of the user you wanna find friend objects for.
     * @return The List of friend objects.
     */
    @Override
    public List<Friend> getFriends(Long userId) {
      return friendRepository.findAllById_UserOrId_FriendAndPendingFalse(userId);
    }

    @Override
    public List<Friend> getFriendRequests(Long userId) {
      return friendRepository.findAllById_FriendAndPendingTrue(userId);
    }

    @Override
    public void addFriend(User user, User friend) {
      FriendId friendId = new FriendId();
      friendId.setFriend(friend);
      friendId.setUser(user);
      friendRepository.save(new Friend(friendId, false, new Timestamp(System.currentTimeMillis())));
    }

    @Override
    public void addFriendRequest(User user, User friend) {
      FriendId friendId = new FriendId();
      friendId.setFriend(friend);
      friendId.setUser(user);
      friendRepository.save(new Friend(friendId, true, new Timestamp(System.currentTimeMillis())));
    }

    @Override
    public Friend getFriendRequest(User user, User friend) {
      FriendId friendId = new FriendId(friend, user);
      Optional<Friend> friendRequest = friendRepository.findById(friendId);
      if(friendRequest.isPresent()) {
        return friendRequest.get();
      } else {
        return null; // TODO
      }
    }

    @Override 
    public void acceptFriendRequest(Friend friendRequest) {
      friendRepository.acceptFriendRequest(friendRequest.getId());
    }
}
