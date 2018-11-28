package com.example.springresttwitterable.utils;

import com.example.springresttwitterable.TestConstants;
import com.example.springresttwitterable.entity.Message;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import javassist.NotFoundException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * DB filler for test classes;
 * <p>
 * Created on 11/16/18.
 * <p>
 * @author Vlad Martinkov
 */

@Transactional
@Component
public class TestDataHelper
{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    ObjectMapper objectMapper;
    
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
    
    public ArrayList checkErrorResponseStructureAndReturnDetailedSubErrorList(String errorResponse) throws IOException
    {
        
        HashMap errorHashMap = objectMapper.readValue(errorResponse, HashMap.class);

        assertThat(errorHashMap.containsKey("apierror"), is(true));

        HashMap innerErrorHashMap = (HashMap) errorHashMap.get("apierror");

        assertThat(innerErrorHashMap.size(), is(5));
        assertThat(innerErrorHashMap.containsKey("status"), is(true));
        assertThat(innerErrorHashMap.get("status"), is("BAD_REQUEST"));
        assertThat(innerErrorHashMap.containsKey("timestamp"), is(true));
        assertThat(TestConstants.extractFromString((String) innerErrorHashMap.get("timestamp")) , is(lessThan(LocalDateTime.now())));
        assertThat(innerErrorHashMap.containsKey("message"), is(true));
        assertThat(innerErrorHashMap.get("message"), is("Validation error"));
        assertThat(innerErrorHashMap.containsKey("debugMessage"), is(true));
        assertNull(innerErrorHashMap.get("debugMessage"));
        assertThat(innerErrorHashMap.containsKey("subErrors"), is(true));

        return (ArrayList) innerErrorHashMap.get("subErrors");
    }
}
