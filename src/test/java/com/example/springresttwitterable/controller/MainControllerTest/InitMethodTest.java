package com.example.springresttwitterable.controller.MainControllerTest;

import com.example.springresttwitterable.controller.MainController;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link MainController} tests.
 * <p>
 * Created on 11/5/18.
 * <p>
 * @author Vlad Martinkov
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InitMethodTest
{
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception
    {
        
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("<title>Twitterable</title>", "var frontendData", "<div id=\"app\"></div>");
    }
    
    @Test
    public void greetingShouldNotReturnUnnecessaryMessage()
    {

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .doesNotContain("Hello World", "I'm not in main template.", "localhost:10000");
    }
}