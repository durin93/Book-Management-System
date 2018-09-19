package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.History;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.support.domain.HistoryType;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private UserService userService;

    private HistoryService historyService;

    @Autowired
    public BookService(BookRepository bookRepository, UserService userService, HistoryService historyService) {
        this.bookRepository = bookRepository;
        this.userService = userService;
        this.historyService = historyService;
    }

    public Book findExistBookById(Long id) {
        return bookRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_BOOK));
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorManager.NOT_EXIST_BOOK));
    }

    public List<Book> findBooksByRender() {
        return bookRepository.findAllByRender(userService.loginUser());
    }

    public List<Book> findBooksByRender(User user) {
        return bookRepository.findAllByRender(user);
    }

    public BookDto regist(BookDto bookDto) {
        User user = userService.loginUser();
        user.checkManager();
        Book book = bookDto.toBook();
        registHistory(book, user, HistoryType.REGIST);
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(BookDto bookDto, Long id) {
        User user = userService.loginUser();
        Book book = findExistBookById(id);
        book.update(user, bookDto);
        registHistory(book, user, HistoryType.UPDATE);
        return book.toBookDto();
    }

    public BookDto delete(Long id) {
        User user = userService.loginUser();
        Book book = findExistBookById(id);
        book.delete(user);
        registHistory(book, user, HistoryType.DELETE);
        return book.toBookDto();
    }

    public BookDto rent(Long id) {
        User user = userService.loginUser();
        Book book = findExistBookById(id);
        book.rentBy(user);
        registHistory(book, user, HistoryType.RENT);
        return book.toBookDto();
    }

    public BookDto giveBack(Long id) {
        User user = userService.loginUser();
        Book book = findExistBookById(id);
        book.giveBack();
        registHistory(book, user, HistoryType.GIVEBACK);
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

    public History registHistory(Book book, User user, HistoryType historyType) {
        return historyService.regist(book, user, historyType);
    }

}
