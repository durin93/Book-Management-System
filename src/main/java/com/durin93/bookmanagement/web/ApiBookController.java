package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.UserDto;
import com.durin93.bookmanagement.service.BookService;
import com.durin93.bookmanagement.service.UserService;
import com.durin93.bookmanagement.support.HttpSessionUtils;
import com.durin93.bookmanagement.support.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.naming.CannotProceedException;
import javax.servlet.http.HttpSession;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/books")
public class ApiBookController {

    private static final Logger logger = LoggerFactory.getLogger(ApiBookController.class);

    private BookService bookService;

    public ApiBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("")
    public ResponseEntity<BookDto> regist(@LoginUser User loginUser, @RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.regist(loginUser,bookDto);
        registedBook.add(linkTo(ApiBookController.class).slash(registedBook.getId()).withSelfRel());
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@LoginUser User loginUser, @PathVariable Long id, @RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.update(loginUser,bookDto, id);
        registedBook.add(linkTo(ApiBookController.class).slash(registedBook.getId()).withSelfRel());
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id) {
        bookService.delete(loginUser,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        BookDto bookDto = bookService.findById(id).toBookDto();
        bookDto.add(linkTo(ApiUserController.class).slash(bookDto.getId()).withSelfRel());
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

}
