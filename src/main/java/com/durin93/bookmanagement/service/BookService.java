package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.NotFoundException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import com.durin93.bookmanagement.support.domain.ErrorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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

    public List<Book> findBooksByRender(){
        return bookRepository.findAllByRender(userService.loginUser());
    }

    public List<Book> findBooksByRender(User user){
        return bookRepository.findAllByRender(user);
    }

    public BookDto regist(BookDto bookDto) {
        User user = userService.loginUser();
        user.checkManager();
        Book book = bookDto.toBook();
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(BookDto bookDto, Long id) {
        User loginUser = userService.loginUser();
        Book book = findExistBookById(id);
        book.update(loginUser,bookDto);
        return book.toBookDto();
    }

    public BookDto delete(Long id) {
        User loginUser = userService.loginUser();
        Book book = findExistBookById(id);
        return book.delete(loginUser).toBookDto();
    }

    public BookDto rent(Long id) {
        Book book = findExistBookById(id);
        return book.rentBy(userService.loginUser()).toBookDto();
    }

    public BookDto giveBack(Long id) {
        Book book = findExistBookById(id);
        book.giveBackBy();
        return book.toBookDto();
    }

    public BookDtos findRentBooks() {
        return BookDtos.of(findBooksByRender());
    }

    public BookDtos findRentBooks(Long id) {
        User render = userService.findById(id);
        return BookDtos.of(findBooksByRender(render));
    }

    public BookDtos search(SearchDto searchDto) {
        String content = searchDto.getContent();
        if(searchDto.isLabelTitle()) {
            return BookDtos.of(bookRepository.findAllByTitleLike("%"+content+"%"));
        }
        if(searchDto.isLabelAuthor()) {
            return BookDtos.of(bookRepository.findAllByAuthorLike("%"+content+"%"));
        }
        return BookDtos.of(bookRepository.findAllBooks(content));
    }

}
