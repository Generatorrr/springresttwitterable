package com.example.springresttwitterable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test constants
 * <p>
 * Created on 11/20/18.
 * <p>
 * @author Vlad Martinkov
 */
public final class TestConstants
{
    
    public static final String testUserName = "test";
    public static final String testUserId = "01234566789";
    public static final String testUserEmail = "test@gmail.com";
    
    public static final String testSubscriberUserName = "subscriber";
    public static final String testSubscriberUserId = "228007";
    public static final String testSubscriberUserEmail = "subscriber@gmail.com";
    
    public static final String newTestMessageText = "Hello, World!!!";
    public static final String newTestMessageTag = "test";
    
    public static final String invalidTestPicturePath = "image/little_kitty_max_size_exception.jpg";
    public static final String validTestPicturePath = "image/little_kitty_valid_size.jpg";
    
    public static final String userNotFoundExceptionMessage = "User not found!";
    public static final String usersNotFoundExceptionMessage = "Users not found!";
    
    public  static byte[] asByteArray(final Object object) {
        
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            final byte[] bytes;
            bytes = mapper.writeValueAsBytes(object);
            return bytes;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  static String asJsonString(final Object object) {

        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            final String jsonString;
            jsonString = mapper.writeValueAsString(object);
            return jsonString;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
