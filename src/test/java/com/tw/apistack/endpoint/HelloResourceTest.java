package com.tw.apistack.endpoint;

import com.tw.apistack.endpoint.dto.Greeting;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jxzhong on 2017/6/27.
 */


public class HelloResourceTest {

    @Test
    public void greeting() throws Exception {
        HelloResource helloResource = new HelloResource();
        Greeting result = helloResource.greeting("Man");
        assertThat(result.getContent()).isEqualTo("Hello, Man!");
        assertThat(result.getId()).isNotNull();
    }


}