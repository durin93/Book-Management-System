package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.service.BookService;
import com.durin93.bookmanagement.support.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CannotProceedException;

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
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@LoginUser User loginUser, @PathVariable Long id, @RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.update(loginUser,bookDto, id);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id) {
        bookService.delete(loginUser,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/rent")
    public ResponseEntity<BookDto> rent(@LoginUser User loginUser, @PathVariable Long id) throws CannotProceedException {
        BookDto registedBook = bookService.rent(loginUser,id);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}/giveBack")
    public ResponseEntity<BookDto> giveBack(@LoginUser User loginUser, @PathVariable Long id) throws CannotProceedException {
        BookDto registedBook = bookService.giveBack(loginUser,id);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        BookDto bookDto = bookService.findById(id).toBookDto();
        addHateoasSelf(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    private void addHateoasSelf(BookDto bookDto){
        bookDto.add(linkTo(ApiBookController.class).slash(bookDto.getId()).withSelfRel());
    }

}
