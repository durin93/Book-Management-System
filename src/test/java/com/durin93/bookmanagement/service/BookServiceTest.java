package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.support.domain.Level;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import com.durin93.bookmanagement.support.test.MockitoTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookServiceTest extends MockitoTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    UserService userService;

    @InjectMocks
    private BookService bookService;


    private User manager;
    private User user;
    private Book book;

    @Before
    public void setUp() {
        manager = new User("manager", "password", "관리자테스터", Level.MANAGER);
        user = new User("user", "password", "사용자테스터", Level.USER);
        book = createBook().toBook();
    }

    @Test
    public void regist() {
        mockWhenLoginManager();
        when(bookRepository.save(any())).thenReturn(book);
        BookDto savedBook = bookService.regist(createBook());
        assertThat(savedBook, is(book.toBookDto()));
        verify(bookRepository, times((1))).save(any());
    }

    @Test
    public void regist_noManager() {
        mockWhenLoginUser();

        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NO_MANAGER);
        bookService.regist(createBook());
        fail();
    }

  /*  @Test
    public void update() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        BookDto updatedBook = bookService.update(createBook(), 1L);
        assertThat(updatedBook, is(createBook()));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());

    }*/

    @Test
    public void update_noManager() {
        mockWhenLoginUser();

        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NO_MANAGER);

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));

        bookService.update(createBook(), 1L);
        fail();
    }



    @Test
    public void delete() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        BookDto deletedBook = bookService.delete(1L);
        assertThat(deletedBook.isDeleted(), is(true));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());

    }

    @Test
    public void delete_noManager() {
        mockWhenLoginUser();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));

        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NO_MANAGER);
        bookService.delete(1L);
        fail();
    }


    @Test
    public void rent() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        BookDto rentBook = bookService.rent(1L);
        assertThat(rentBook, is(book.toBookDto()));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());
    }

    @Test
    public void rent_already() {
        mockWhenLoginManager();
        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book.rentBy(user)));

        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_RENT);
        bookService.rent(1L);
        fail();
    }

    @Test
    public void giveBack() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book.rentBy(user)));
        BookDto giveBackBook = bookService.giveBack( 1L);
        assertThat(giveBackBook.isRentable(), is(true));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());
    }

    @Test
    public void giveBack_already() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_GIVEBACK);
        bookService.giveBack( 1L);
        fail();
    }

    @Test
    public void findRentBooks() {
        mockWhenLoginManager();

        BookDtos bookDtos;

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookRepository.findAllByRender(any())).thenReturn(bookList);
        bookDtos = bookService.findRentBooks();
        assertTrue(bookDtos.hasBook(book.toBookDto()));
        verify(bookRepository, times((1))).findAllByRender(any());
    }

    @Test
    public void search() {
        mockWhenLoginManager();

        BookDtos bookDtos;

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);

        when(bookRepository.findAllByTitleLike(any())).thenReturn(bookList);
        bookDtos = bookService.search(new SearchDto("title","기본"));
        assertTrue(bookDtos.hasBook(book.toBookDto()));
        verify(bookRepository, times((1))).findAllByTitleLike(any());
        verify(bookRepository, times((0))).findAllByAuthorLike(any());
        verify(bookRepository, times((0))).findAllBooks(any());

    }


    public BookDto createBook() {
        return new BookDto("스페인 너는 자유다", "손미나",LocalDate.of(2006, 7, 28),340,582);
    }

    public void mockWhenLoginManager(){
        when(userService.loginUser()).thenReturn(manager);
    }

    public void mockWhenLoginUser(){
        when(userService.loginUser()).thenReturn(user);
    }

}
