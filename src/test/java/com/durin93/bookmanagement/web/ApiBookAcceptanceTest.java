package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.dto.BookDto;
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

public class ApiBookAcceptanceTest extends AcceptanceTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiBookAcceptanceTest.class);

    @Test
    public void regist() {
        BookDto bookDto = createBookDefault();

        ResponseEntity<BookDto> response =
                basicAuthTemplate(findManagerUser()).postForEntity("/api/books", bookDto, BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(bookDto));
        assertNotNull(response.getBody().getLink("self"));
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

        ResponseEntity<BookDto> dbBook = getResource(resourceUrl,BookDto.class,findManagerUser());
        assertThat(dbBook.getStatusCode(), is(HttpStatus.OK));
        assertThat(dbBook.getBody(), is(updateBookDto));
        assertNotNull(dbBook.getBody().getLink("self"));
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

        ResponseEntity<BookDto> response = getResource(resourceUrl,BookDto.class,findManagerUser());
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    public BookDto createBookDefault(){
        return new BookDto("토끼와 거북이", "토끼와 거북이의 경주 이야기.");
    }

}
