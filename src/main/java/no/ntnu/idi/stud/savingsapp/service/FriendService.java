package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.FriendId;
import no.ntnu.idi.stud.savingsapp.model.user.User;

/**
 * Service class for handling friend operations.
 */
@Service
public interface FriendService {
    List<Friend> getFriends(Long userId);
    
    List<Friend> getFriendRequests(Long userId);

    void addFriend(User user, User friend);

    void addFriendRequest(User user, User friend);

    Friend getFriendRequest(User user, User friend);

    void acceptFriendRequest(Friend friendRequest);
}
