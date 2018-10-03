package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    private UserService userService;


    @Autowired
    public BookService(BookRepository bookRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
    }

    public Book findExistBookById(Long id) {
        return bookRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_BOOK));
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_BOOK));
    }

    public Book registBook(BookDto bookDto) {
        return bookRepository.save(bookDto.toBook());
    }

    public List<Book> findBooksByRender() {
        return bookRepository.findAllByRender(userService.loginUser());
    }

    public List<Book> findBooksByRender(User user) {
        return bookRepository.findAllByRender(user);
    }

    @Transactional
    public BookDto regist(BookDto bookDto) {
        User user = userService.loginUser();
        user.checkManager();
        return registBook(bookDto).toBookDto();
    }

    @Transactional
    public BookDto update(BookDto bookDto, Long id) {
        Book book = findExistBookById(id);
        book.update(userService.loginUser(), bookDto);
        return book.toBookDto();
    }

    @Transactional
    public BookDto delete(Long id) {
        Book book = findExistBookById(id);
        book.delete(userService.loginUser());
        return book.toBookDto();
    }

    @Transactional
    public BookDto rent(Long id) {
        Book book = findExistBookById(id);
        book.rentBy(userService.loginUser());
        return book.toBookDto();
    }

    @Transactional
    public BookDto giveBack(Long id) {
        Book book = findExistBookById(id);
        book.giveBack();
        return book.toBookDto();
    }

    public BookDtos findRentBooks() {
        return BookDtos.of(findBooksByRender());
    }

    public BookDtos findRentBooks(Long id) {
        return BookDtos.of(findBooksByRender(userService.findById(id)));
    }

    public BookDtos search(SearchDto searchDto) {
        String content = searchDto.getContent();
        if (searchDto.isSearchTypeTitle()) {
            return BookDtos.of(bookRepository.findAllByTitleLike("%" + content + "%"));
        }
        if (searchDto.isSearchTypeAuthor()) {
            return BookDtos.of(bookRepository.findAllByAuthorLike("%" + content + "%"));
        }
        return BookDtos.of(bookRepository.findAllBooks(content));
    }


}
