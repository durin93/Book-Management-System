package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertTrue;

public class UserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private User manager;

    private User user;

    private Book book;

    @Before
    public void setUp() {
        manager = new User("manager", "password", "관리자테스터", Level.MANAGER);
        user = new User("user", "password", "테스터", Level.USER);
    }

    @Test
    public void matchPassword() {
        assertTrue(user.matchPassword("password"));
    }

    @Test
    public void matchPassword_wrongPassword() {
        thrown.expect(UnAuthenticationException.class);
        thrown.expectMessage(ErrorManager.WRONG_PASSWORD);
        user.matchPassword("cassword");
    }

    @Test
    public void checkManager() {
        assertTrue(manager.checkManager());
    }

    @Test
    public void checkManager_user() {
        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NO_MANAGER);
        user.checkManager();
    }


}
