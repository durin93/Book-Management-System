package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.web.ApiHistoryController;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class HitoryDtoTest {

    private HistoryDto historyDto;

    @Before
    public void setUp() {
        historyDto = new HistoryDto();
    }

    @Test
    public void addLink() {
        assertThat(historyDto.addLink().getLink("self"), is((linkTo(ApiHistoryController.class).withSelfRel())));
    }

}
