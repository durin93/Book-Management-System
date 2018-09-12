package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import org.junit.Before;
import org.junit.Test;

import javax.naming.AuthenticationException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserTest {

    private User user;

    @Before
    public void setUp(){
        user = new User(1L, "testId", "password","테스터");
    }

    @Test(expected = UnAuthenticationException.class)
    public void matchPassword() {
        user.matchPassword("cassword");
    }

    @Test(expected = UnAuthorizationException.class)
    public void checkManager() {
        user.checkManager();
    }

    @Test
    public void toUserDto(){
        assertThat(user.toUserDto(), is(createUser("testId")));
    }


    public UserDto createUser(String userId){
        return new UserDto(1L, userId, "password", "테스터");
    }




}
