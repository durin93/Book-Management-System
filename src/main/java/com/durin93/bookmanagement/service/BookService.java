package com.durin93.bookmanagement.service;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.exception.DeleteException;
import com.durin93.bookmanagement.repository.BookRepository;
import com.durin93.bookmanagement.repository.UserRepository;
import com.durin93.bookmanagement.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public User findUserById(Long id) {
        System.out.println("찾기");
        return userRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public Book findExistBookById(Long id) {
        return bookRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(DeleteException::new);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(DeleteException::new);
    }

    public List<Book> findBooksByRender(){
        return bookRepository.findAllByRender(loginUser());
    }

    public List<Book> findBooksByRender(User user){
        return bookRepository.findAllByRender(user);
    }

    public BookDto regist(BookDto bookDto) {
        loginUser().checkManager();
        Book book = bookDto.toBook();
        return bookRepository.save(book).toBookDto();
    }

    public BookDto update(BookDto bookDto, Long id) {
        loginUser().checkManager();
        Book book = findExistBookById(id);
        book.update(bookDto);
        return book.toBookDto();
    }

    public BookDto delete(Long id) {
        loginUser().checkManager();
        Book book = findExistBookById(id);
        return book.delete().toBookDto();
    }

    public BookDto rent(Long id) {
        Book book = findExistBookById(id);
        return book.rentBy(loginUser()).toBookDto();
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
        User render = findUserById(id);
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
