package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.exception.UnAuthorizationException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import com.durin93.bookmanagement.support.domain.ErrorManager;
import com.durin93.bookmanagement.support.domain.Level;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


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
        book = new Book(1L,"기본도서", "기본작가");
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
        thrown.expectMessage(ErrorManager.NO_MANAGER.getMessage());
        bookService.regist(createBook());
        fail();
    }

    @Test
    public void update() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        BookDto updatedBook = bookService.update(createBook(), book.getId());
        assertThat(updatedBook, is(createBook()));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());

    }

    @Test
    public void update_noManager() {
        mockWhenLoginUser();

        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NO_MANAGER.getMessage());

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));

        bookService.update(createBook(), book.getId());
        fail();
    }



    @Test
    public void delete() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        BookDto deletedBook = bookService.delete( book.getId());
        assertThat(deletedBook.getDeleted(), is(true));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());

    }

    @Test
    public void delete_noManager() {
        mockWhenLoginUser();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));

        thrown.expect(UnAuthorizationException.class);
        thrown.expectMessage(ErrorManager.NO_MANAGER.getMessage());
        bookService.delete(book.getId());
        fail();
    }


    @Test
    public void rent() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        BookDto rentBook = bookService.rent( book.getId());
        assertThat(rentBook, is(book.toBookDto()));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());
    }

    @Test
    public void rent_already() {
        mockWhenLoginManager();
        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book.rentBy(user)));

        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_RENT.getMessage());
        bookService.rent(book.getId());
        fail();
    }

    @Test
    public void giveBack() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book.rentBy(user)));
        BookDto giveBackBook = bookService.giveBack( book.getId());
        assertThat(giveBackBook.getRentable(), is(true));
        verify(bookRepository, times((1))).findByIdAndIsDeletedIsFalse(any());
    }

    @Test
    public void giveBack_already() {
        mockWhenLoginManager();

        when(bookRepository.findByIdAndIsDeletedIsFalse(anyLong())).thenReturn(Optional.of(book));
        thrown.expect(RentalException.class);
        thrown.expectMessage(ErrorManager.ALREADY_GIVEBACK.getMessage());
        bookService.giveBack( book.getId());
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
        return new BookDto("새로운도서", "새로운작가");
    }

    public void mockWhenLoginManager(){
        when(userService.loginUser()).thenReturn(manager);
    }

    public void mockWhenLoginUser(){
        when(userService.loginUser()).thenReturn(user);
    }

}
