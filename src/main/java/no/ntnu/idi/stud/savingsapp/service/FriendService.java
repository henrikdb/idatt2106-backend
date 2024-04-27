package no.ntnu.idi.stud.savingsapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;

/**
 * Service class for handling friend operations.
 */
@Service
public interface FriendService {
    List<Friend> getFriends(Long userId);
    
    List<Friend> getFriendRequests(Long userId);
}
