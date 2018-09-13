package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiBookAcceptanceTest extends AcceptanceTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiBookAcceptanceTest.class);

    @Test
    public void regist() {
        BookDto createBookDto = createBookDefault();

        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findManagerUser()),createBookDto);

        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", requestEntity, BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(createBookDto));
        assertNotNull(response.getBody().getSelfDescription());
    }

    @Test
    public void regist_fail_unAuthorization() {
        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findNormalUser()),createBookDefault());

        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", requestEntity, BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }


    @Test
    public void update() {
        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findManagerUser()),createBookDefault());
        String resourceUrl =
                template().
                        postForEntity("/api/books", requestEntity, BookDto.class)
                        .getBody().getLink("self").getHref();

        BookDto updateBookDto = new BookDto("토끼와거북이", "어린왕자 이야기");

        requestEntity = httpEntity(jwtHeaders(findManagerUser()),updateBookDto);

        ResponseEntity<BookDto> response =
        template().exchange(resourceUrl,HttpMethod.PUT,requestEntity,BookDto.class);


        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(updateBookDto));
        assertNotNull(response.getBody().getSelfDescription());
    }

    @Test
    public void update_fail_unAuthorization() {
        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findManagerUser()),createBookDefault());
        String resourceUrl =
                template().
                        postForEntity("/api/books", requestEntity, BookDto.class)
                        .getBody().getLink("self").getHref();
        BookDto updateBookDto = new BookDto("토끼와거북이", "어린왕자 이야기");
        requestEntity = httpEntity(jwtHeaders(findNormalUser()),updateBookDto);

        ResponseEntity<BookDto> response =
                template().exchange(resourceUrl,HttpMethod.PUT,requestEntity,BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete() {
        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findManagerUser()),createBookDefault());
        String resourceUrl =
                template().
                        postForEntity("/api/books", requestEntity, BookDto.class)
                        .getBody().getLink("self").getHref();

        requestEntity = httpEntity(requestEntity.getHeaders());
        ResponseEntity<BookDto> response =
                template().exchange(resourceUrl,HttpMethod.DELETE,requestEntity,BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void rent() {
        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findManagerUser()),createBookDefault());
        String resourceUrl =
                template().
                        postForEntity("/api/books", requestEntity, BookDto.class)
                        .getBody().getLink("self").getHref();

        requestEntity = httpEntity(jwtHeaders(findNormalUser()));

        ResponseEntity<BookDto> response =
                template().exchange(resourceUrl+"/rent",HttpMethod.PUT,requestEntity,BookDto.class);
        assertFalse(response.getBody().getRentable());
    }

    @Test
    public void giveBack() {
        HttpEntity<BookDto> requestEntity = httpEntity(jwtHeaders(findManagerUser()),createBookDefault());
        String resourceUrl =
                template().
                        postForEntity("/api/books", requestEntity, BookDto.class)
                        .getBody().getLink("self").getHref();

        requestEntity = httpEntity(jwtHeaders(findNormalUser()));
        template().exchange(resourceUrl+"/rent",HttpMethod.PUT,requestEntity,BookDto.class);

        ResponseEntity<BookDto> response =
                template().exchange(resourceUrl+"/giveBack",HttpMethod.PUT,requestEntity,BookDto.class);
        assertTrue(response.getBody().getRentable());
    }


    public BookDto createBookDefault() {
        return new BookDto("스페인 너는 자유다", "30만 독자의 사랑을 받은 서른앓이 에세이 여전히 유효한 그녀의 외침 지금 떠나라");
    }


}
