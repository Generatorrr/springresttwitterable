package com.example.springresttwitterable.controller;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Wrapper for controller tests
 * <p>
 * Created on 11/14/18.
 * <p>
 * @author Vlad Martinkov
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public abstract class BaseControllerTest
{
}
