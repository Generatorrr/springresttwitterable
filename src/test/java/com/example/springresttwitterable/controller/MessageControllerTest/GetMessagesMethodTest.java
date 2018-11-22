package com.example.springresttwitterable.controller.MessageControllerTest;

import com.example.springresttwitterable.controller.BaseControllerTest;
import com.example.springresttwitterable.controller.MessageController;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.message.MessageDTO;
import com.example.springresttwitterable.entity.dto.user.UserAuthorDTO;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.repository.MessageRepository;
import com.example.springresttwitterable.repository.UserRepository;
import com.example.springresttwitterable.utils.TestDataFiller;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javassist.NotFoundException;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link MessageController}'s "getMessages" method.
 * <p>
 * Created on 11/5/18.
 * <p>
 * @author Vlad Martinkov
 */

@Transactional
public class GetMessagesMethodTest extends BaseControllerTest
{

    @Value("${testing.host.origin.local}")
    private String hostOrigin;

    @LocalServerPort
    private int port;
    
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TestDataFiller testDataFiller;

    private MockMvc mvc;

    private boolean wasInserted = false;
    private UserAuthorDTO convertedTestUserDTO;
    
    
    
    @Before
    public void setUp() throws NotFoundException
    {
        
        if (!wasInserted) {

            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
            User user = testDataFiller.createTestUserAndOneHundredMessagesAndReturnUserAuthorDTO();
            convertedTestUserDTO = userMapper.convertToAuthorDTO(user);
            wasInserted = true;
        }
    }
    
    @Test
    public void getErrorWithoutMockUser() throws Exception {
        mvc.perform(get(String.format("%s:%s/message", hostOrigin, port)))
                .andExpect(status().isForbidden());
    }
    
    @Test
    @WithMockUser("test")
    public void getFirstPageMessagesWithDefaultPager() throws Exception {
        
        String response = mvc.perform(get(String.format("%s:%s/message", hostOrigin, port))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        MessageDTO messageDTO = objectMapper.readValue(response, MessageDTO.class);
        
        // Test page size and message author is like expected
        assertThat(messageDTO.getMessages().size(), is(10));
        assertThat(messageDTO.getPage().getCurrentPage(), is(0));
        assertThat(messageDTO.getPage().getTotalPages(), is(10));
        assertThat(messageDTO.getPage().getTotalDocs(), is(100));
        assertThat(messageDTO.getMessages().size(), is(messageDTO.getPage().getPageSize()));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), is(convertedTestUserDTO));
        
        // Test "messages" field in DTO has all needed fields
        assertThat(messageDTO.getMessages(), everyItem(hasProperty("id")));
        assertThat(messageDTO.getMessages(), everyItem(hasProperty("text")));
        assertThat(messageDTO.getMessages(), everyItem(hasProperty("tag")));
        assertThat(messageDTO.getMessages(), everyItem(hasProperty("author")));
        assertThat(messageDTO.getMessages(), everyItem(hasProperty("edited")));
        assertThat(messageDTO.getMessages(), everyItem(hasProperty("filename")));
        
        // Test "author" field in "messages" field in DTO has all needed fields
        assertThat(messageDTO.getMessages().get(0).getAuthor(), hasProperty("id"));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), hasProperty("name"));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), hasProperty("email"));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), hasProperty("userpic"));

        // Test "author" field in "messages" field in DTO hasn't got unwanted fields
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("gender")));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("locale")));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("lastVisit")));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("roles")));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("messages")));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("subscribtions")));
        assertThat(messageDTO.getMessages().get(0).getAuthor(), not(hasProperty("subscribers")));
    }

    @Test
    @WithMockUser("test")
    public void getMessagesWithPagerSettingsSize() throws Exception {

        String response = mvc.perform(get(String.format("%s:%s/message?size=100", hostOrigin, port))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        MessageDTO messageDTO = objectMapper.readValue(response, MessageDTO.class);

        assertThat(messageDTO.getMessages().size(), is(100));
        assertThat(messageDTO.getMessages().size(), is(messageDTO.getPage().getPageSize()));
        assertThat(messageDTO.getPage().getCurrentPage(), is(0));
        assertThat(messageDTO.getPage().getTotalPages(), is(1));
        assertThat(messageDTO.getPage().getTotalDocs(), is(100));
    }

    @Test
    @WithMockUser("test")
    public void getMessagesWithPagerSettingsSizeAndPage() throws Exception {

        String response = mvc.perform(get(String.format("%s:%s/message?size=25&page=2", hostOrigin, port))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        MessageDTO messageDTO = objectMapper.readValue(response, MessageDTO.class);

        assertThat(messageDTO.getMessages().size(), is(25));
        assertThat(messageDTO.getMessages().size(), is(messageDTO.getPage().getPageSize()));
        assertThat(messageDTO.getPage().getCurrentPage(), is(2));
        assertThat(messageDTO.getPage().getTotalPages(), is(4));
        assertThat(messageDTO.getPage().getTotalDocs(), is(100));
    }
}