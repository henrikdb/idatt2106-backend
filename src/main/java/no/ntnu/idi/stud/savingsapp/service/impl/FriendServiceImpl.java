package no.ntnu.idi.stud.savingsapp.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
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

}
