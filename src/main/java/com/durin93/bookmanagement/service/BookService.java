package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.support.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class BookService {

    private UserRepository userRepository;

    private BookRepository bookRepository;

    private JwtManager jwtManager;

    @Autowired
    public BookService(UserRepository userRepository, BookRepository bookRepository, JwtManager jwtManager) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.jwtManager = jwtManager;
    }

    public User loginUser() {
        return userRepository.findByUserId(jwtManager.decode()).orElseThrow(NullPointerException::new);
    }

    public Book findBookById(Long id) {
        return bookRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(NullPointerException::new);
    }

    public BookDto regist(BookDto bookDto) {
        loginUser().checkManager();
        Book book = bookDto.toBook();
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(BookDto bookDto, Long id) {
        loginUser().checkManager();
        Book book = findBookById(id);
        book.update(bookDto);
        return book.toBookDto();
    }

    public BookDto delete(Long id) {
        loginUser().checkManager();
        Book book = findBookById(id);
        return book.delete().toBookDto();
    }

    public BookDto rent(Long id) {
        Book book = findBookById(id);
        return book.rentBy(loginUser()).toBookDto();
    }

    public BookDto giveBack(Long id) {
        Book book = findBookById(id);
        book.giveBackBy();
        return book.toBookDto();
    }

    public List<BookDto> findAllBook() {
        List<Book> books = bookRepository.findAllByRender(loginUser());

        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book:books) {
            bookDtos.add(book.toBookDto());
        }
        
        return bookDtos;
    }
}
