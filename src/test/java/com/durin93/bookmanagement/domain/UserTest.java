package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.UserDto;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

    @Test(expected = AuthenticationException.class)
    public void matchPassword() throws AuthenticationException {
        user.matchPassword("cassword");
    }

    @Test
    public void toUserDto(){
        assertThat(user.toUserDto(), is(new UserDto(1L,"testId","password","테스터")));
    }





}
