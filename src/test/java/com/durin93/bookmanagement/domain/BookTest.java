package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

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
        book = new BookDto("여행의 기술", "알랭 드 보통", LocalDate.of(2011, 12, 10), 328, 470).toBook();
    }

    @Test
    public void rent() {
        book.rentBy(user);
        assertThat(book.getRender(), is(user));
        assertTrue(book.existRender());
    }

    @Test
    public void rent_and_same_rent() {
        book.rentBy(user);

        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_RENT);
        book.rentBy(user);
        fail();
    }

    @Test
    public void giveBack() {
        book.rentBy(user);
        book.giveBack();
        assertNull(book.getRender());
    }

    @Test
    public void giveBack_already_giveBack() {
        book.rentBy(user);
        book.giveBack();

        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_GIVEBACK);
        book.giveBack();
        fail();
    }

    @Test
    public void update() {

        book.update(manager, createBook());
        assertThat(book.toBookDto(), is(createBook()));
    }


    @Test
    public void delete() {
        book.delete(manager);
        assertTrue(book.getDeleted());
    }

    @Test
    public void checkRentable(){
        assertThat(book.checkRentable(),is(true));
    }

    @Test
    public void checkRentable_fail(){
        book.rentBy(user);
        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_RENT);
        book.checkRentable();
        fail();
    }

    @Test
    public void existRender(){
        assertThat(book.existRender(),is(false));
    }

    @Test
    public void existRender_already(){
        book.rentBy(user);
        assertThat(book.existRender(),is(true));
    }

    public BookDto createBook() {
        return new BookDto("스페인 너는 자유다", "손미나", LocalDate.of(2006, 7, 28), 340, 582);
    }


}
