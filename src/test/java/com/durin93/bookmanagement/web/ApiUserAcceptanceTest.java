package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


public class ApiUserAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);

    @Test
    public void regist() {
        UserDto createUser = createUserDefault();

        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users", createUser, UserDto.class);

        assertNotNull(response.getBody().getLink("self"));
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(createUser));
    }

    @Test
    public void regist_fail_valid() {
        UserDto createUser = createUserDefault();
        createUser.setUserId("t");

        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users", createUser, UserDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void regist_fail_unAuthentication() {
        ResponseEntity<UserDto> response = template().postForEntity("/api/users", new UserDto("durin93", "password", "name"), UserDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void login() {
        Map<String, String> loginUser =  loginUserMap();
        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users/authentication", loginUser, UserDto.class);

        assertNotNull(response.getBody().getLink("self"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getUserId(), is("durin93"));

        log.debug(response.getBody().toString());
    }

    @Test
    public void login_fail_unAuthentication() {
        Map<String, String> loginUser =  loginUserMap();
        loginUser.replace("password","12345");
        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users/authentication", loginUser, UserDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }


    @Test
    public void show() {

        String resourceUrl = getResourceUrl(createUser(new UserDto("test2", "password", "name")), "self");

        ResponseEntity<UserDto> response =
                template().getForEntity(resourceUrl, UserDto.class);

        assertNotNull(response.getBody().getLink("self"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getUserId(), is("test2"));

        log.debug(response.getBody().toString());
    }


}
