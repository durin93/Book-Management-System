package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ApiBookAcceptanceTest extends AcceptanceTest {

    @Test
    public void regist() {
        BookDto createBookDto = createBookDefault();

        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", jwtEntity(findManagerUser(), createBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody(), is(createBookDto));
        assertNotNull(response.getBody().getLink("self"));
        assertNotNull(response.getBody().getSelfDescription());
    }

    @Test
    public void regist_fail_valid_title() {
        BookDto createBookDto = createBookDefault();
        createBookDto.setTitle("손");

        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", jwtEntity(findManagerUser(), createBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void regist_fail_valid_minus_weight() {
        BookDto createBookDto = createBookDefault();
        createBookDto.setWeight(-150);

        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", jwtEntity(findManagerUser(), createBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    @Test
    public void regist_fail_unAuthorization() {
        ResponseEntity<BookDto> response =
                template().postForEntity("/api/books", jwtEntity(findNormalUser(), createBookDefault()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update() {
        String resourceUrl = createBook(createBookDefault()).getLink("self").getHref();

        BookDto updateBookDto = createBookDefault3();
        ResponseEntity<BookDto> response = requestPUT(resourceUrl, jwtEntity(findManagerUser(), updateBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(updateBookDto));
        assertNotNull(response.getBody().getLink("self"));
    }

    @Test
    public void update_fail_unAuthorization() {
        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");

        BookDto updateBookDto = createBookDefault3();

        ResponseEntity<BookDto> response =
                requestPUT(resourceUrl, jwtEntity(findNormalUser(), updateBookDto), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete() {
        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");

        ResponseEntity<BookDto> response = requestDELETE(resourceUrl, jwtEntity(findManagerUser()), BookDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void rent() {
        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");
        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().isRentable());
        assertNotNull(response.getBody().getLink("self"));
        assertNotNull(response.getBody().getLink("render"));
    }

    @Test
    public void rent_alreadyRent() {
        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");
        requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void giveBack() {
        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");

        requestPUT(resourceUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class);
        ResponseEntity<BookDto> response = requestPUT(resourceUrl + "/giveBack", jwtEntity(findNormalUser()), BookDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().isRentable());
        assertNotNull(response.getBody().getLink("self"));

    }


    @Test
    public void show() {
        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");

        ResponseEntity<BookDto> response =
                requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class);


        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(createBookDefault()));
        assertNotNull(response.getBody().getLink("self"));
    }


    @Test
    public void showRentBooks() {
        BookDto bookDto = createBook(createBookDefault());
        String createBookUrl = getResourceUrl(bookDto, "self");
        requestPUT(createBookUrl + "/rent", jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books/users/" + findNormalUser().getId(), jwtEntity(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().hasBook(requestGET(createBookUrl, jwtEntity(findNormalUser()), BookDto.class).getBody()));
    }

    @Test
    public void search_title() {

        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");
        String resourceUrl2 = getResourceUrl(createBook(createBookDefault2()), "self");

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?searchType=title&content=스페인", jwtEntityForm(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().hasBook(createBook));
        assertFalse(response.getBody().hasBook(createBook2));
    }

    @Test
    public void search_author() {

        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");
        String resourceUrl2 = getResourceUrl(createBook(createBookDefault2()), "self");

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?searchType=author&content=알랭 드 보통", jwtEntityForm(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().hasBook(createBook));
        assertTrue(response.getBody().hasBook(createBook2));
    }

    @Test
    public void search_all() {

        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");
        String resourceUrl2 = getResourceUrl(createBook(createBookDefault2()), "self");

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?searchType=all&content=알랭 드 보통", jwtEntityForm(findNormalUser()), BookDtos.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().hasBook(createBook));
        assertTrue(response.getBody().hasBook(createBook2));
    }

    @Test
    public void search_all2() {

        String resourceUrl = getResourceUrl(createBook(createBookDefault()), "self");
        String resourceUrl2 = getResourceUrl(createBook(createBookDefault2()), "self");

        BookDto createBook = requestGET(resourceUrl, jwtEntity(findNormalUser()), BookDto.class).getBody();
        BookDto createBook2 = requestGET(resourceUrl2, jwtEntity(findNormalUser()), BookDto.class).getBody();

        ResponseEntity<BookDtos> response =
                requestGET("/api/books?searchType=all&content=두린", jwtEntityForm(findNormalUser()), BookDtos.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertFalse(response.getBody().hasBook(createBook));
        assertFalse(response.getBody().hasBook(createBook2));
    }

}
