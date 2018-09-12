package com.durin93.bookmanagement.web.webtestclient;

import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ApiUserAcceptanceTest extends AcceptanceTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);

    @Test
    public void regist() {
        UserDto createUser = new UserDto("test2", "password", "name");

        UserDto response =
                webTestClient.post().uri("/api/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(Mono.just(createUser), UserDto.class)
                        .exchange()
                        .expectStatus().isCreated()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                        .expectBody(UserDto.class)
                        .consumeWith(result -> assertNotNull(result.getResponseBody().getLink("self")))
                        .consumeWith(result -> assertThat(result.getResponseBody(), is(createUser)))
                        .returnResult().getResponseBody();

        logger.debug(response.toString());
    }

    @Test
    public void login() {
        UserDto loginUser = new UserDto("durin93", "1234");

        UserDto response =
                webTestClient.post().uri("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(Mono.just(loginUser), UserDto.class)
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                        .expectBody(UserDto.class)
                        .consumeWith(result -> assertNotNull(result.getResponseBody().getLink("self")))
                        .consumeWith(result -> assertThat(result.getResponseBody().getUserId(), is("durin93")))
                        .returnResult().getResponseBody();

        logger.debug(response.toString());
    }


}
