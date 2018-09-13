package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiBookAcceptanceTest extends AcceptanceTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiBookAcceptanceTest.class);

    @Test
    public void regist() {
        BookDto bookDto = createBookDefault();

        ResponseEntity<BookDto> response =
                basicAuthTemplate(findManagerUser()).postForEntity("/api/books", bookDto, BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(bookDto));
        assertNotNull(response.getBody().getSelfDescription());
    }

    @Test
    public void regist_fail_unAuthorization() {
        BookDto bookDto = createBookDefault();

        ResponseEntity<BookDto> response =
                basicAuthTemplate(findByUserId("lsc109")).postForEntity("/api/books", bookDto, BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }


    @Test
    public void update() {
        String resourceUrl =
                basicAuthTemplate(findManagerUser()).
                        postForEntity("/api/books", createBookDefault(), BookDto.class)
                        .getBody().getLink("self").getHref();

        BookDto updateBookDto = new BookDto("토끼와거북이", "어린왕자 이야기");
        basicAuthTemplate(findManagerUser()).put(resourceUrl, updateBookDto);

        ResponseEntity<BookDto> dbBook = getResource(resourceUrl, BookDto.class, findManagerUser());
        assertThat(dbBook.getStatusCode(), is(HttpStatus.OK));
        assertThat(dbBook.getBody(), is(updateBookDto));
        assertNotNull(dbBook.getBody().getSelfDescription());
    }

    @Test
    public void delete() {
        String resourceUrl =
                basicAuthTemplate(findManagerUser()).
                        postForEntity("/api/books", createBookDefault(), BookDto.class)
                        .getBody()
                        .getLink("self")
                        .getHref();

        basicAuthTemplate(findManagerUser()).delete(resourceUrl);

        ResponseEntity<BookDto> response = getResource(resourceUrl, BookDto.class, findManagerUser());
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void rent() {
        String resourceUrl =
                basicAuthTemplate(findManagerUser()).
                        postForEntity("/api/books", createBookDefault(), BookDto.class)
                        .getBody()
                        .getLink("self")
                        .getHref();

        basicAuthTemplate(findByUserId("lsc109")).put(resourceUrl + "/rent", null);
        ResponseEntity<BookDto> updatedBook = getResource(resourceUrl, BookDto.class, findManagerUser());
        assertFalse(updatedBook.getBody().getRentable());
    }

    @Test
    public void giveBack() {
        String resourceUrl =
                basicAuthTemplate(findManagerUser()).
                        postForEntity("/api/books", createBookDefault(), BookDto.class)
                        .getBody()
                        .getLink("self")
                        .getHref();

        basicAuthTemplate(findByUserId("lsc109")).put(resourceUrl + "/giveBack", null);
        ResponseEntity<BookDto> updatedBook = getResource(resourceUrl, BookDto.class, findManagerUser());
        assertTrue(updatedBook.getBody().getRentable());
    }


    public BookDto createBookDefault() {
        return new BookDto("스페인 너는 자유다", "30만 독자의 사랑을 받은 서른앓이 에세이 여전히 유효한 그녀의 외침 지금 떠나라");
    }


}
