package com.example.springresttwitterable.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Test class for {@link UserRepository}
 * <p>
 * Created on 11/2/18.
 * <p>
 * @author Vlad Martinkov
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest
{
    
    @Autowired
    private TestEntityManager testEntityManager;
    
    @Autowired UserRepository userRepository;

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findByName()
    {
    }
}