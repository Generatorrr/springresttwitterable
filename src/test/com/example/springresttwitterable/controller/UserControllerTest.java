package com.example.springresttwitterable.controller;

import com.example.springresttwitterable.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;

/**
 * Tests for {@link UserController}
 * <p>
 * Created on 11/2/18.
 * <p>
 * @author Vlad Martinkov
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest
{
    
    private final MockMvc mockMvc;
    private final UserService userService;

    public UserControllerTest(MockMvc mockMvc, UserService userService)
    {
        this.mockMvc = mockMvc;
        this.userService = userService;
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void updateProfile()
    {
    }

    @Test
    public void subscribe()
    {
    }

    @Test
    public void unsubscribe()
    {
    }

    @Test
    public void userList()
    {
    }
    
    @Test
    public void getAllUsers()
    {
    }
}