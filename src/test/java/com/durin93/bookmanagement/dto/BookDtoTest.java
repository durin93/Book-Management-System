package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.web.ApiBookController;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BookDtoTest {

    private BookDto bookDto;

    @Before
    public void setUp() {
        bookDto = new BookDto();
    }

    @Test
    public void addLink() {
        assertThat(bookDto.addLink(Optional.empty()).getLink("self"), is((linkTo(ApiBookController.class).withSelfRel())));
    }

}
