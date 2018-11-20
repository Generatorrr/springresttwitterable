package com.example.springresttwitterable.utils;

import com.example.springresttwitterable.TestConstants;
import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import javassist.NotFoundException;

/**
 * DB filler for test classes;
 * <p>
 * Created on 11/16/18.
 * <p>
 * @author Vlad Martinkov
 */

@Transactional
@Component
public class TestDataFiller
{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    public User createTestUserAndOneHundredMessagesAndReturnUserAuthorDTO() throws NotFoundException {
        
        User subscriber = new User();
        subscriber.setId(TestConstants.testSubscriberUserId);
        subscriber.setName(TestConstants.testSubscriberUserName);
        subscriber.setEmail(TestConstants.testSubscriberUserEmail);
        subscriber.setLastVisit(LocalDateTime.now());
        entityManager.persist(subscriber);

        User testUser = new User();
        String userId = TestConstants.testUserId;
        testUser.setId(userId);
        testUser.setName(TestConstants.testUserName);
        testUser.setEmail(TestConstants.testUserEmail);
        testUser.setLastVisit(LocalDateTime.now());
        entityManager.persist(testUser);
        
        entityManager.flush();
        Optional<User> userHaveToBeInDB = userRepository.findById(userId);

        if (!userHaveToBeInDB.isPresent()) {
            throw new NotFoundException(TestConstants.userNotFoundExceptionMessage);
        }
        User userInDb = userHaveToBeInDB.get();
        for (int i = 0; i < 100; i++) {
            Message message = new Message();
            message.setText(String.format("test%d", i));
            message.setTag("test");
            message.setAuthor(userInDb);
            userInDb.getMessages().add(message);
            entityManager.persist(userInDb);
        }
        entityManager.flush();
        
        return testUser;
    }
}
