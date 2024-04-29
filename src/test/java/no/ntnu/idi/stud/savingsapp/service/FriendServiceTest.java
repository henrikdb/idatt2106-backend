package no.ntnu.idi.stud.savingsapp.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.FriendId;
import no.ntnu.idi.stud.savingsapp.model.user.User;
import no.ntnu.idi.stud.savingsapp.repository.FriendRepository;
import no.ntnu.idi.stud.savingsapp.service.impl.FriendServiceImpl;

@SpringBootTest
public class FriendServiceTest {
    @Mock
    private FriendRepository friendRepository;

    @InjectMocks
    private FriendServiceImpl friendService;

    private User user;
    private User friend;
    private Friend friendEntity;
    private FriendId friendId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        friend = new User();
        user.setId(1L);
        friend.setId(2L);

        friendId = new FriendId(user, friend);
        friendEntity = new Friend(friendId, false, null);
    }

    @Test
    public void testGetFriends() {
        when(friendRepository.findAllById_UserOrId_FriendAndPendingFalse(user.getId())).thenReturn(Arrays.asList(friendEntity));
        List<Friend> result = friendService.getFriends(user.getId());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(friendEntity, result.get(0));
    }
}
