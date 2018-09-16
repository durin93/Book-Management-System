package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
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
        String resourceUrl = createBook(createBookDefault());

        BookDto updateBookDto = new BookDto("내가 사랑한 유럽 TOP10", "정여울");
        ResponseEntity<BookDto> response = requestPUT(resourceUrl, jwtEntity(findManagerUser(), updateBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(updateBookDto));
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));
    }

    @Test
    public void update_fail_unAuthorization() {
        String resourceUrl = createBook(createBookDefault());

        BookDto updateBookDto = new BookDto("내가 사랑한 유럽 TOP10", "정여울");

        ResponseEntity<BookDto> response =
                requestPUT(resourceUrl, jwtEntity(findNormalUser(), updateBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete() {
        String resourceUrl = createBook(createBookDefault());

        ResponseEntity<BookDto> response = requestDELETE(resourceUrl, jwtEntity(findManagerUser()), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void rent() {
        String resourceUrl = createBook(createBookDefault());

        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().getRentable());
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));

    }

    @Test
    public void giveBack() {
        String resourceUrl = createBook(createBookDefault());

        requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/giveBack", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().getRentable());
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));

    }


    @Test
    public void show() {
        String resourceUrl = createBook(createBookDefault());

        ResponseEntity<BookDto> response =
                requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class);


        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(createBookDefault()));
        assertNotNull(response.getBody().getSelfDescription().getLink("self"));
    }

    @Test
    public void showRentBooks() {
        String resourceUrl = createBook(createBookDefault());
        requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);

        ResponseEntity<BookDtos> response =
                requestGET("/api/books/mybooks", jwtEntity(findNormalUser()), BookDtos.class);


        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().hasBook(requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody()));
    }

    @Test
    public void search_title() {

        String resourceUrl = createBook(createBookDefault());
        String resourceUrl2 = createBook(createBookDefault2());

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?label=title&content=스페인", jwtEntityForm(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().hasBook(createBook));
        assertFalse(response.getBody().hasBook(createBook2));
    }

    @Test
    public void search_author() {

        String resourceUrl = createBook(createBookDefault());
        String resourceUrl2 = createBook(createBookDefault2());

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?label=author&content=알랭 드 보통", jwtEntityForm(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().hasBook(createBook));
        assertTrue(response.getBody().hasBook(createBook2));
    }

    @Test
    public void search_all() {

        String resourceUrl = createBook(createBookDefault());
        String resourceUrl2 = createBook(createBookDefault2());

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?label=all&content=알랭 드 보통", jwtEntityForm(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().hasBook(createBook));
        assertTrue(response.getBody().hasBook(createBook2));
    }

    @Test
    public void search_all2() {

        String resourceUrl = createBook(createBookDefault());
        String resourceUrl2 = createBook(createBookDefault2());

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?label=all&content=두린", jwtEntityForm(findNormalUser()), BookDtos.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().hasBook(createBook));
        assertFalse(response.getBody().hasBook(createBook2));
    }


    public BookDto createBookDefault() {
        return new BookDto("스페인 너는 자유다", "손미나");
    }

    public BookDto createBookDefault2() {
        return new BookDto("여행의 기술", "알랭 드 보통");
    }

    public String createBook(BookDto bookDto) {
        return template().
                postForEntity("/api/books", jwtEntity(findManagerUser(), bookDto), BookDto.class)
                .getBody().getLink("self").getHref();
    }

}
