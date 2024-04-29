package no.ntnu.idi.stud.savingsapp.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import no.ntnu.idi.stud.savingsapp.model.user.Friend;
import no.ntnu.idi.stud.savingsapp.model.user.FriendId;
import no.ntnu.idi.stud.savingsapp.model.user.Role;
import no.ntnu.idi.stud.savingsapp.model.user.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-h2.yml")
public class FriendRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FriendRepository friendRepository;

    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void init() {
        user1 = new User(
            null,                      
            "User",                   
            "User",      
            "user@example.com", 
            null,  
            null, 
            "SomeEncryptedPassword1", 
            new Timestamp(System.currentTimeMillis()), 
            Role.USER,                 
            Collections.emptyList(),  
            null,
            null,       
            null);        
        user2 = new User(
            null,                        
            "User2",                  
            "Five",                   
            "user5@example.com", 
            null,          
            null,              
            "SomeEncryptedPassword2", 
            new Timestamp(System.currentTimeMillis()),
            Role.USER,                
            Collections.emptyList(),  
            null,           
            null,     
            null);   
        
        user3 = new User(
            null,                      
            "User3",                   
            "Three",      
            "user3@example.com", 
            null,  
            null, 
            "SomeEncryptedPassword3", 
            new Timestamp(System.currentTimeMillis()), 
            Role.USER,                 
            Collections.emptyList(),  
            null,
            null,       
            null);

        // Persist all users 
        entityManager.persist(user1);          
        entityManager.persist(user2);
        entityManager.persist(user3);

        // user1 is friends with user2
        Friend friend12 = new Friend(new FriendId(user1, user2), false, new Timestamp(System.currentTimeMillis()));
        // user1 has sent a friend request to user3
        Friend friend13 = new Friend(new FriendId(user1, user3), true, new Timestamp(System.currentTimeMillis()));

        entityManager.persist(friend12);
        entityManager.persist(friend13);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    @DisplayName("Test testGetFriends")
    public void testGetFriends() {
        List<Friend> friends = friendRepository.findAllById_UserOrId_FriendAndPendingFalse(user1.getId());
        assertThat(friends).hasSize(1);
        assertThat(friends.get(0).getId().getUser().getId()).isEqualTo(user1.getId());
        assertThat(!friends.get(0).isPending());
        assertThat(friends.get(0).getId().getFriend().getId()).isEqualTo(user2.getId());
    }

    @Test
    @DisplayName("Test testGetFriendRequests")
    public void testGetFriendRequests() {
        List<Friend> friends = friendRepository.findAllById_FriendAndPendingTrue(user3.getId());
        assertThat(friends).hasSize(1);
        assertThat(friends.get(0).getId().getUser().getId()).isEqualTo(user1.getId());
        assertThat(friends.get(0).isPending());
        assertThat(friends.get(0).getId().getFriend().getId()).isEqualTo(user3.getId());
    }

    @Test
    @DisplayName("Test acceptFriendRequest")
    public void testAcceptFriendRequest() {
        FriendId friendId = new FriendId(user1, user3);
        friendRepository.acceptFriendRequest(friendId);
        
        List<Friend> friends = friendRepository.findAllById_UserOrId_FriendAndPendingFalse(user1.getId());
        assertThat(friends).hasSize(2);
        assertThat(!friends.get(0).isPending());
        assertThat(!friends.get(1).isPending());
    }
}
