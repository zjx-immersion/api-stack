package com.tw.apistack.security;

import com.tw.apistack.ApiStackApplication;
import com.tw.apistack.security.core.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiStackApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {
    private String base;

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate template;

    @Before
    public void init() throws Exception {
        this.base = "http://localhost:" + port + "/api";
    }


    @Test
    public void shouldRefreshToken() throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        ResponseEntity<String> response = template.postForEntity(base + "/login", user, String.class);
        assertEquals(200, response.getStatusCode().value());

        ResponseEntity<String> refreshResponse = template.getForEntity(base + "/secure/token/refresh", String.class);
        assertEquals(401, refreshResponse.getStatusCode().value());
    }
}