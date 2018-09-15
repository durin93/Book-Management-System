package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiBookAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiBookAcceptanceTest.class);

    @Test
    public void regist() {
        BookDto createBookDto = createBookDefault();

        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", jwtEntity(findManagerUser(), createBookDto), BookDto.class);


        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(createBookDto));
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));
    }


    @Test
    public void regist_fail_unAuthorization() {
        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", jwtEntity(findNormalUser(), createBookDefault()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }


    @Test
    public void update() {
        String resourceUrl = createBookUrl();

        BookDto updateBookDto = new BookDto("내가 사랑한 유럽 TOP10", "정여울");
        ResponseEntity<BookDto> response = requestPUT(resourceUrl, jwtEntity(findManagerUser(), updateBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(updateBookDto));
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));
    }

    @Test
    public void update_fail_unAuthorization() {
        String resourceUrl = createBookUrl();

        BookDto updateBookDto = new BookDto("내가 사랑한 유럽 TOP10", "정여울");

        ResponseEntity<BookDto> response =
                requestPUT(resourceUrl, jwtEntity(findNormalUser(), updateBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete() {
        String resourceUrl = createBookUrl();

        ResponseEntity<BookDto> response = requestDELETE(resourceUrl, jwtEntity(findManagerUser()), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void rent() {
        String resourceUrl = createBookUrl();

        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().getRentable());
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));

    }

    @Test
    public void giveBack() {
        String resourceUrl = createBookUrl();

        requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/giveBack", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().getRentable());
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));

    }


    public BookDto createBookDefault() {
        return new BookDto("스페인 너는 자유다", "손미나");
    }

    public String createBookUrl() {
        return template().
                postForEntity("/api/books", jwtEntity(findManagerUser(), createBookDefault()), BookDto.class)
                .getBody().getLink("self").getHref();
    }

}
