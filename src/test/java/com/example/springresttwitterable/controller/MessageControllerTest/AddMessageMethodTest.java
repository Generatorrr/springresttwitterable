package com.example.springresttwitterable.controller.MessageControllerTest;

import com.example.springresttwitterable.TestConstants;
import com.example.springresttwitterable.controller.BaseControllerTest;
import com.example.springresttwitterable.controller.MessageController;
import com.example.springresttwitterable.error.RestExceptionHandler;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.message.MessageDTO;
import com.example.springresttwitterable.entity.dto.message.NewMessageDTO;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javassist.NotFoundException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link MessageController}'s "addMessage" method.
 * <p>
 * Created on 11/5/18.
 * <p>
 * @author Vlad Martinkov
 */

@Transactional
public class AddMessageMethodTest extends BaseControllerTest
{

    @Value("${testing.host.origin.local}")
    private String hostOrigin;
    
    @Value("${test.images.location}")
    private String testImagesFolder;

    @LocalServerPort
    private int port;
    
    @Autowired
    MessageController messageController;
    
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
    private TestingAuthenticationToken authentication;

    private boolean wasInserted = false;
    private UserAuthorDTO convertedTestUserDTO;
    private File validPicture;
    private File invalidPicture;
    private MockMultipartFile mockMultipartFile;
    private NewMessageDTO newMessageDTO;
    
    
    
    @Before
    public void setUp() throws NotFoundException, IOException
    {
        
        if (!wasInserted) {
            
            User user = testDataFiller.createTestUserAndOneHundredMessagesAndReturnUserAuthorDTO();
            convertedTestUserDTO = userMapper.convertToAuthorDTO(user);
            wasInserted = true;
            authentication = new TestingAuthenticationToken(user, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            validPicture = new File(getClass().getClassLoader().getResource(TestConstants.validTestPicturePath).getFile());
            invalidPicture = new File(getClass().getClassLoader().getResource(TestConstants.invalidTestPicturePath).getFile());
            newMessageDTO = new NewMessageDTO();
            newMessageDTO.setText(TestConstants.newTestMessageText);
            newMessageDTO.setTag(TestConstants.newTestMessageTag);
            mockMultipartFile = new MockMultipartFile("little_kitten", new FileInputStream(validPicture));
        }
    }
    
    @Test
    public void getErrorWithoutMockUser() throws Exception {

        MockMvcBuilders
                .standaloneSetup(messageController)
                .setControllerAdvice(new RestExceptionHandler())
                .build()
            .perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                    .file("file", mockMultipartFile.getBytes())
                    .param("text", "")
                    .param("tag", newMessageDTO.getTag())
            )
            .andExpect(status().isForbidden());
    }

    @Test
    public void postMessageWithEmptyText() throws Exception {

        mvc = MockMvcBuilders
                .standaloneSetup(messageController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();

        String test = mvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                        .file("file", mockMultipartFile.getBytes())
                        .param("text", "")
                        .param("tag", newMessageDTO.getTag())
                        .principal(authentication)
        )
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        String response = mvc.perform(get(String.format("%s:%s/message", hostOrigin, port))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        MessageDTO messageDTO = objectMapper.readValue(response, MessageDTO.class);

        assertThat(messageDTO.getPage().getTotalPages(), is(11));
        assertThat(messageDTO.getPage().getTotalDocs(), is(101));
    }
    
    @Test
    public void postMessageWithValidImageFile() throws Exception {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        
            mvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                    .file("file", mockMultipartFile.getBytes())
                    .param("text", newMessageDTO.getText())
                    .param("tag", newMessageDTO.getTag())
                    .principal(authentication)
                )
                .andExpect(status().isOk());

        String response = mvc.perform(get(String.format("%s:%s/message", hostOrigin, port))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        MessageDTO messageDTO = objectMapper.readValue(response, MessageDTO.class);

        assertThat(messageDTO.getPage().getTotalPages(), is(11));
        assertThat(messageDTO.getPage().getTotalDocs(), is(101));
    }
}