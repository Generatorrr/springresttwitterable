package com.example.springresttwitterable.controller.MessageControllerTest;

import com.example.springresttwitterable.TestConstants;
import com.example.springresttwitterable.controller.BaseControllerTest;
import com.example.springresttwitterable.controller.MessageController;
import com.example.springresttwitterable.entity.User;
import com.example.springresttwitterable.entity.dto.message.NewMessageDTO;
import com.example.springresttwitterable.entity.mapper.UserMapper;
import com.example.springresttwitterable.error.RestExceptionHandler;
import com.example.springresttwitterable.repository.MessageRepository;
import com.example.springresttwitterable.repository.UserRepository;
import com.example.springresttwitterable.utils.TestDataHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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
    private TestDataHelper testDataHelper;
    
    private MockMvc messageControllerMvc;
    private TestingAuthenticationToken authentication;
    private boolean wasInserted = false;
    private File validPicture;
    private File invalidPicture;
    private MockMultipartFile mockMultipartFile;
    private MockMultipartFile mockMultipartInvalidFile;
    private NewMessageDTO newMessageDTO;
    
    
    
    @Before
    public void setUp() throws NotFoundException, IOException
    {
        
        if (!wasInserted) {
            
            messageControllerMvc = MockMvcBuilders
                .standaloneSetup(messageController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
            
            User user = testDataHelper.createTestUserAndOneHundredMessagesAndReturnUserAuthorDTO();
            wasInserted = true;
            authentication = new TestingAuthenticationToken(user, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            validPicture = new File(getClass().getClassLoader().getResource(TestConstants.validTestPicturePath).getFile());
            invalidPicture = new File(getClass().getClassLoader().getResource(TestConstants.invalidTestPicturePath).getFile());
            newMessageDTO = new NewMessageDTO();
            newMessageDTO.setText(TestConstants.newTestMessageText);
            newMessageDTO.setTag(TestConstants.newTestMessageTag);
            mockMultipartFile = new MockMultipartFile("little_kitten", new FileInputStream(validPicture));
            mockMultipartInvalidFile = new MockMultipartFile("little_kitten", new FileInputStream(invalidPicture));
        }
    }
    
    @Test
    public void getErrorWithoutMockUser() throws Exception {

        MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build()
            .perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                    .file("file", mockMultipartFile.getBytes())
                    .param("text", newMessageDTO.getText())
                    .param("tag", newMessageDTO.getTag())
            )
            .andExpect(status().isForbidden());
    }

    @Test
    public void postMessageWithEmptyTextAndGetBadRequest() throws Exception {

        String errorResponse = messageControllerMvc.perform(
            multipart(String.format("%s:%s/message", hostOrigin, port))
                .file("file", mockMultipartFile.getBytes())
                .param("text", "")
                .param("tag", newMessageDTO.getTag())
                .principal(authentication)
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn().getResponse().getContentAsString();

        ArrayList subErrorsList = testDataHelper.checkErrorResponseStructureAndReturnDetailedSubErrorList(errorResponse);
        assertThat(subErrorsList.size(), is(1));

        HashMap subErrorHashMap = (HashMap) subErrorsList.get(0);
        
        assertThat(subErrorHashMap.containsKey("object"), is(true));
        assertThat(subErrorHashMap.get("object"), is("newMessageDTO"));
        assertThat(subErrorHashMap.containsKey("field"), is(true));
        assertThat(subErrorHashMap.get("field"), is("text"));
        assertThat(subErrorHashMap.containsKey("rejectedValue"), is(true));
        assertThat(subErrorHashMap.get("rejectedValue"), is(""));
        assertThat(subErrorHashMap.containsKey("message"), is(true));
        assertThat(subErrorHashMap.get("message"), is("Please, fill the message"));
    }

    @Test
    public void postMessageWithInvalidFileType() throws Exception {

        String errorResponse = messageControllerMvc.perform(
            multipart(String.format("%s:%s/message", hostOrigin, port))
                .param("file", "")
                .param("text", "Hello")
                .param("tag", newMessageDTO.getTag())
                .principal(authentication)
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn().getResponse().getContentAsString();

        ArrayList subErrorsList = testDataHelper.checkErrorResponseStructureAndReturnDetailedSubErrorList(errorResponse);
        assertThat(subErrorsList.size(), is(1));

        HashMap subErrorHashMap = (HashMap) subErrorsList.get(0);

        assertThat(subErrorHashMap.containsKey("object"), is(true));
        assertThat(subErrorHashMap.get("object"), is("newMessageDTO"));
        assertThat(subErrorHashMap.containsKey("field"), is(true));
        assertThat(subErrorHashMap.get("field"), is("file"));
        assertThat(subErrorHashMap.containsKey("rejectedValue"), is(true));
        assertThat(subErrorHashMap.get("rejectedValue"), is(""));
        assertThat(subErrorHashMap.containsKey("message"), is(true));
        assertThat((String) subErrorHashMap.get("message"), containsString("Cannot convert value of type 'java.lang.String'"));
    }

    @Test
    public void postMessageWithInvalidTagLength() throws Exception {

        String errorResponse = messageControllerMvc.perform(
            multipart(String.format("%s:%s/message", hostOrigin, port))
                .file("file", mockMultipartFile.getBytes())
                .param("text", "Hello")
                .param("tag", RandomStringUtils.randomAlphabetic(256))
                .principal(authentication)
        )
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn().getResponse().getContentAsString();

        ArrayList subErrorsList = testDataHelper.checkErrorResponseStructureAndReturnDetailedSubErrorList(errorResponse);
        assertThat(subErrorsList.size(), is(1));

        HashMap subErrorHashMap = (HashMap) subErrorsList.get(0);

        assertThat(subErrorHashMap.containsKey("object"), is(true));
        assertThat(subErrorHashMap.get("object"), is("newMessageDTO"));
        assertThat(subErrorHashMap.containsKey("field"), is(true));
        assertThat(subErrorHashMap.get("field"), is("tag"));
        
        String rejectedString = (String) subErrorHashMap.get("rejectedValue");
        assertNotNull(rejectedString);
        assertThat(rejectedString.length(), is(256));
        assertThat(subErrorHashMap.containsKey("message"), is(true));
        assertThat(subErrorHashMap.get("message"), is("Tag is too long"));
    }

    @Test
    public void postMessageWithInvalidImage() throws Exception {

        String errorResponse = messageControllerMvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                        .file("file", mockMultipartInvalidFile.getBytes())
                        .param("text", "Hello")
                        .param("tag", "some_tag")
                        .principal(authentication)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        ArrayList subErrorsList = testDataHelper.checkErrorResponseStructureAndReturnDetailedSubErrorList(errorResponse);
        assertThat(subErrorsList.size(), is(1));

        HashMap subErrorHashMap = (HashMap) subErrorsList.get(0);

        assertThat(subErrorHashMap.containsKey("object"), is(true));
        assertThat(subErrorHashMap.get("object"), is("newMessageDTO"));
        assertThat(subErrorHashMap.containsKey("field"), is(true));
        assertThat(subErrorHashMap.get("field"), is("file"));
        assertThat(subErrorHashMap.containsKey("rejectedValue"), is(true));
        assertThat(subErrorHashMap.get("rejectedValue"), is("MultipartFile"));
        assertThat(subErrorHashMap.containsKey("message"), is(true));
        assertThat(subErrorHashMap.get("message"), is("Please, don't upload files with size more than 500 kB!"));
    }

    @Test
    public void postValidMessageWithoutTag() throws Exception {

        messageControllerMvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                        .file("file", mockMultipartFile.getBytes())
                        .param("text", newMessageDTO.getText())
                        .principal(authentication)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void postValidMessageWithoutImage() throws Exception {

        messageControllerMvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                        .param("text", newMessageDTO.getText())
                        .param("tag", newMessageDTO.getTag())
                        .principal(authentication)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void postValidMessageWithoutImageAndWithoutTag() throws Exception {

        messageControllerMvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                        .param("text", newMessageDTO.getText())
                        .principal(authentication)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void postMessageWithValidImageFile() throws Exception {

        messageControllerMvc.perform(
                multipart(String.format("%s:%s/message", hostOrigin, port))
                        .file("file", mockMultipartFile.getBytes())
                        .param("text", newMessageDTO.getText())
                        .param("tag", newMessageDTO.getTag())
                        .principal(authentication)
        )
                .andExpect(status().isOk());
    }
}