package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class ApiUserAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiUserAcceptanceTest.class);

    @Test
    public void regist() {
        UserDto createUser = new UserDto("test1", "password", "name");

        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users", createUser, UserDto.class);

        assertNotNull(response.getBody().getLink("self"));
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(createUser));
    }

    @Test
    public void regist_fail_unAuthentication() {
        ResponseEntity<UserDto> response = template().postForEntity("/api/users", new UserDto("durin93", "password", "name"), UserDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void login() {
        UserDto loginUser = new UserDto("durin93", "1234","");

        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users/authentication", loginUser, UserDto.class);

        assertNotNull(response.getBody().getLink("self"));
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getUserId(), is("durin93"));

        log.debug(response.getBody().toString());
    }

    @Test
    public void login_fail_unAuthentication() {
        ResponseEntity<UserDto> response =
                template().postForEntity("/api/users/authentication", new UserDto("durin93", "12345",""), UserDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void showRentBooks() {
        BookDto bookDto = createBook(createBookDefault());
        String createBookUrl = getResourceUrl(bookDto, "self");
        bookDto = requestPUT(createBookUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books/users/"+findNormalUser().getId(), jwtEntity(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().hasBook(requestGET(createBookUrl, jwtEntity(findNormalUser()), BookDto.class).getBody()));
    }

}
