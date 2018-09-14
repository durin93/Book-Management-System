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

    private UserRepository userRepository;

    private BookRepository bookRepository;

    @Autowired
    public BookService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public BookDto regist(String loginUser, BookDto bookDto) {
        findUserByUserId(loginUser).checkManager();
        Book book = bookDto.toBook();
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(String loginUser, BookDto bookDto, Long id) {
        findUserByUserId(loginUser).checkManager();
        Book book = findBookById(id);
        book.update(bookDto);
        return book.toBookDto();
    }

    public void delete(String loginUser, Long id) throws CannotProceedException {
        findUserByUserId(loginUser).checkManager();
        Book book = findBookById(id);
        book.delete();
    }

    public BookDto rent(String loginUser, Long id) throws CannotProceedException {
        Book book = bookRepository.findFirstByAndIdAndIsDeletedIsFalse(id).orElseThrow(NullPointerException::new);
        book.rent(findUserByUserId(loginUser));
        return book.toBookDto();
    }

    public BookDto giveBack(String loginUser, Long id) throws CannotProceedException {
        Book book = bookRepository.findFirstByAndIdAndIsDeletedIsFalse(id).orElseThrow(NullPointerException::new);
        book.giveBack();
        return book.toBookDto();
    }
}
