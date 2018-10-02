package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.web.ApiUserController;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserDtoTest {

    private UserDto userDto;

    @Before
    public void setUp() {
        userDto = new UserDto();
    }

    @Test
    public void addLink() {
        assertThat(userDto.addLink().getLink("self"), is((linkTo(ApiUserController.class).withSelfRel())));
    }

}
