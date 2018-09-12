package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.exception.UnAuthenticationException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(NullPointerException::new);
    }


    public BookDto regist(User loginUser, BookDto bookDto) {
        loginUser.checkManager();
        Book book = bookDto.toBook();
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(User loginUser, BookDto bookDto, Long id) {
        loginUser.checkManager();
        Book book = findById(id);
        return bookRepository.save(book.update(bookDto)).toBookDto();
    }

    public void delete(User loginUser, Long id) {
        loginUser.checkManager();
        bookRepository.delete(findById(id));
    }
}
