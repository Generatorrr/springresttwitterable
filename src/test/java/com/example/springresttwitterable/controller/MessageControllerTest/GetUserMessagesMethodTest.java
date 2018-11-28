package com.example.springresttwitterable.controller.MessageControllerTest;

import com.example.springresttwitterable.TestConstants;
import com.example.springresttwitterable.controller.BaseControllerTest;
import com.example.springresttwitterable.controller.MessageController;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.ChannelDTO;
import com.example.springresttwitterable.entity.dto.user.UserAuthorDTO;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.UserRepository;
import com.example.springresttwitterable.utils.TestDataHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import javassist.NotFoundException;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link MessageController}'s "getUserMessages" method.
 * <p>
 * Created on 11/5/18.
 * <p>
 * @author Vlad Martinkov
 */

@Transactional
public class GetUserMessagesMethodTest extends BaseControllerTest
{

    @Value("${testing.host.origin.local}")
    private String hostOrigin;

    @LocalServerPort
    private int port;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserMapper userMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestDataHelper testDataHelper;
    
    private MockMvc mvc;
    
    private boolean wasInserted = false;
    private UserAuthorDTO convertedTestUserDTO;
    private TestingAuthenticationToken authentication;
    
    @Before
    public void setUp() throws NotFoundException
    {
        
        if (!wasInserted) {
            User user = testDataHelper.createTestUserAndOneHundredMessagesAndReturnUserAuthorDTO();
            convertedTestUserDTO = userMapper.convertToAuthorDTO(user);
            wasInserted = true;
            authentication = new TestingAuthenticationToken(user, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
    
    @Test
    public void getErrorWithoutMockUser() throws Exception {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        
        mvc.perform(get(String.format("%s:%s/message/user/%s", hostOrigin, port, convertedTestUserDTO.getId())))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUserChannelViaChannelDto() throws Exception {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        String response = mvc.perform(get(String.format("%s:%s/message/user/%s", hostOrigin, port, convertedTestUserDTO.getId()))
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ChannelDTO channelDTO = objectMapper.readValue(response, ChannelDTO.class);

        assertThat(channelDTO, hasProperty("messages"));
        assertThat(channelDTO, hasProperty("userChannel"));
        assertThat(channelDTO, hasProperty("subscriptionsCount"));
        assertThat(channelDTO, hasProperty("subscribersCount"));
        // "hasProperty" method deletes "is" part of property name. Searching for better library...
        assertThat(channelDTO, hasProperty("subscriber"));
        assertThat(channelDTO, hasProperty("currentUser"));
        
        assertThat(channelDTO.getMessages().size(), is(100));
        assertThat(channelDTO.isCurrentUser(), is(true));
        assertThat(channelDTO.isSubscriber(), is(false));
        assertThat(channelDTO.getUserChannel(), is(convertedTestUserDTO));
        assertThat(channelDTO.getSubscribersCount(), is(0));
        assertThat(channelDTO.getSubscriptionsCount(), is(0));
        assertThat(channelDTO.getUserChannel(), is(convertedTestUserDTO));
    }

    @Test
    public void getUserChannelWithOneSubscriber() throws Exception {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        Optional<User> userOptional = userRepository.findById(TestConstants.testUserId);
        Optional<User> subscriberUserOptional = userRepository.findById(TestConstants.testSubscriberUserId);
        
        if (!userOptional.isPresent() || !subscriberUserOptional.isPresent()) {
            throw new NotFoundException(TestConstants.usersNotFoundExceptionMessage);
        }
        
        User user = userOptional.get();
        User subscriber = subscriberUserOptional.get();
        
        user.getSubscribers().add(subscriber);
        user.getSubscribtions().add(subscriber);
        userRepository.save(user);
        userRepository.flush();
        
        String response = mvc.perform(get(String.format("%s:%s/message/user/%s", hostOrigin, port, TestConstants.testUserId))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        ChannelDTO channelDTO = objectMapper.readValue(response, ChannelDTO.class);

        assertThat(channelDTO, hasProperty("messages"));
        assertThat(channelDTO, hasProperty("userChannel"));
        assertThat(channelDTO, hasProperty("subscriptionsCount"));
        assertThat(channelDTO, hasProperty("subscribersCount"));
        // "hasProperty" method deletes "is" part of property name. Searching for better library...
        assertThat(channelDTO, hasProperty("subscriber"));
        assertThat(channelDTO, hasProperty("currentUser"));

        assertThat(channelDTO.getMessages().size(), is(100));
        assertThat(channelDTO.isCurrentUser(), is(true));
        assertThat(channelDTO.isSubscriber(), is(false));
        assertThat(channelDTO.getUserChannel(), is(convertedTestUserDTO));
        assertThat(channelDTO.getSubscribersCount(), is(1));
        assertThat(channelDTO.getSubscriptionsCount(), is(1));
        assertThat(channelDTO.getUserChannel(), is(convertedTestUserDTO));
    }
}