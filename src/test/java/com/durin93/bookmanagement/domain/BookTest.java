package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.support.domain.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private User manager;
    private User user;
    private Book book;

    @Before
    public void setUp() {
        manager = new User("manager", "password", "관리자테스터", Level.MANAGER);
        user = new User("user", "password", "사용자테스터", Level.USER);
        book = new Book("기본도서", "기본작가");
    }

    @Test
    public void rent() {
        book.rentBy(user);
        assertThat(book.getRender(), is(user));
        assertFalse(book.checkRender());
    }

    @Test
    public void rent_and_same_rent() {
        book.rentBy(user);

        thrown.expect(RentalException.class);
        thrown.expectMessage("대여중인 도서입니다.");
        book.rentBy(user);
        fail();
    }

    @Test
    public void giveBack() {
        book.rentBy(user);
        book.giveBackBy();
        assertNull(book.getRender());
    }

    @Test
    public void giveBack_already_giveBack() {
        book.rentBy(user);
        book.giveBackBy();

        thrown.expect(RentalException.class);
        thrown.expectMessage("이미 반납된 도서입니다.");
        book.giveBackBy();
        fail();
    }

    @Test
    public void update() {
        book.update(createBook());
        assertThat(book.toBookDto(), is(createBook()));
    }


    @Test
    public void delete() {
        book.delete();
        assertTrue(book.getDeleted());
    }


    @Test
    public void checkManager() {
        assertTrue(manager.checkManager());
    }

    public BookDto createBook() {
        return new BookDto("새로운도서", "새로운작가");
    }


}
