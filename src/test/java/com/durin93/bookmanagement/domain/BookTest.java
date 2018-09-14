package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CannotProceedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BookTest {

    private User user;
    private Book book;

    @Before
    public void setUp(){
        user = new User(1L, "testId", "password","테스터");
        book = new Book("기본","기본저자");
    }

    @Test
    public void rent() throws CannotProceedException {
        book.rent(user);
        assertThat(book.getRender(), is(user));
        assertTrue(user.getRentBooks().contains(book));
    }

    public UserDto createUser(String userId){
        return new UserDto(1L, userId, "password", "테스터");
    }
    public BookDto createBook(){
        return new BookDto(1L,"제목","작가",true);
    }




}
