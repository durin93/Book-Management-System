package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.support.domain.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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
        book = new Book("기본도서", "기본작가");
    }

    @Test
    public void matchPassword() {
        assertTrue(user.matchPassword("password"));
    }

    @Test
    public void matchPassword_wrongPassword() {
        thrown.expect(UnAuthenticationException.class);
        thrown.expectMessage("비밀번호가 틀렸습니다.");
        user.matchPassword("cassword");
    }

    @Test
    public void checkManager() {
        assertTrue(manager.checkManager());
    }

    @Test
    public void checkManager_user() {
        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage("관리자만 접근 가능합니다.");
        user.checkManager();
    }

    @Test
    public void rentBook(){
        user.rentBook(book);
        assertTrue(user.getRentBooks().contains(book));
    }

    @Test
    public void giveBack(){
        user.rentBook(book);
        assertTrue(user.getRentBooks().contains(book));
        user.giveBackBook(book);
        assertFalse(user.getRentBooks().contains(book));
    }




}
