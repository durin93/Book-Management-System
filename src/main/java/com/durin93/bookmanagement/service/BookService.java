package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.CannotProceedException;
import javax.transaction.Transactional;

@Service
@Transactional
public class BookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public BookDto regist(String loginUser, BookDto bookDto) {
        findByUserId(loginUser).checkManager();
        Book book = bookDto.toBook();
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(String loginUser, BookDto bookDto, Long id) {
        findByUserId(loginUser).checkManager();
        Book book = findById(id);
        return bookRepository.save(book.update(bookDto)).toBookDto();
    }

    public void delete(String loginUser, Long id) {
        findByUserId(loginUser).checkManager();
        bookRepository.delete(findById(id));
    }

    public BookDto rent(String loginUser, Long id) throws CannotProceedException {
        Book book = bookRepository.findFirstByAndIdAndRentableIsTrue(id).orElseThrow(NullPointerException::new);
        book.rent(findByUserId(loginUser));
        return book.toBookDto();
    }

    public BookDto giveBack(String loginUser, Long id) throws CannotProceedException {
        Book book = bookRepository.findFirstByAndIdAndRentableIsFalse(id).orElseThrow(NullPointerException::new);
        book.giveBack(findByUserId(loginUser));
        return book.toBookDto();
    }
}
