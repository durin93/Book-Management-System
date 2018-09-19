package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertTrue;

public class UserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private User manager;

    private User user;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static final String PASSWORD1234 = "$2a$10$cd8Yu/5zEeRZ.Z.iwa9H/uIJEwsI/nYR84zdgP30tqDq4DmzTOaIW";

    @Before
    public void setUp() {
        manager = new User("manager", PASSWORD1234, "관리자테스터", Level.MANAGER);
        user = new User("user", PASSWORD1234, "테스터", Level.USER);
    }

    @Test
    public void matchPassword() {
        assertTrue(user.matchPassword("1234",passwordEncoder));
    }

    @Test
    public void matchPassword_wrongPassword() {
        thrown.expect(UnAuthenticationException.class);
        thrown.expectMessage(ErrorManager.WRONG_PASSWORD);
        user.matchPassword("12345",passwordEncoder);
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
