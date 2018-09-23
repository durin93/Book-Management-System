package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.HistoryDto;
import com.durin93.bookmanagement.dto.HistoryDtos;
import com.durin93.bookmanagement.support.domain.HistoryType;
import com.durin93.bookmanagement.support.test.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ApiHistoryAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiHistoryAcceptanceTest.class);

    @Test
    public void show() {
        Long bookId = createBook(createBookDefault()).getId();

        ResponseEntity<HistoryDto> response =
                requestGET("/api/histories/" + bookId, jwtEntityForm(findManagerUser()), HistoryDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getHistoryType(), is(HistoryType.REGIST.getName()));
        assertNotNull(response.getBody().getLink("self"));

    }

    @Test
    public void search_book() {
        Long bookId = createBook(createBookDefault()).getId();
        ResponseEntity<HistoryDtos> response =
                requestGET("/api/histories?searchType=book&id=" + String.valueOf(bookId), jwtEntityForm(findManagerUser()), HistoryDtos.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getSize(), is(1));
    }

    @Test
    public void search_user() {
        ResponseEntity<HistoryDtos> response =
                requestGET("/api/histories?searchType=user&id=1", jwtEntityForm(findManagerUser()), HistoryDtos.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getSize(), not(0));
    }

    @Test
    public void search_all() {
        ResponseEntity<HistoryDtos> response =
                requestGET("/api/histories?searchType=all", jwtEntityForm(findManagerUser()), HistoryDtos.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getSize(), not(0));
    }

}
