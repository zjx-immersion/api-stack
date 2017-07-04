package com.tw.apistack;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jxzhong on 2017/6/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiStackApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloAPITest {

    private static final String API_PATH = "/api/greeting";


    @Value("${local.server.port}")
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
        RestAssured.baseURI = "http://localhost";

    }

    @Test
    public void should_get_status_200_when_call_greeting() throws Exception {
        String name = "World";
        RestAssured.
                given().
                accept(ContentType.JSON).
                when().
                get(API_PATH).
                then().
                statusCode(HttpStatus.SC_OK).
                contentType(ContentType.JSON).
                body("content", Matchers.equalTo(String.format("Hello, %s!", name)));
    }
}
