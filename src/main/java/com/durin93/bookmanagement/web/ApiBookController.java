package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.service.BookService;
import com.durin93.bookmanagement.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.CannotProceedException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/books")
public class ApiBookController {

    private static final Logger log = LoggerFactory.getLogger(ApiBookController.class);

    private JwtService jwtService;

    private BookService bookService;

    @Autowired
    public ApiBookController(JwtService jwtService, BookService bookService) {
        this.jwtService = jwtService;
        this.bookService = bookService;
    }


    @PostMapping("")
    public ResponseEntity<BookDto> regist(@RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.regist(jwtService.getUserId(), bookDto);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.update(jwtService.getUserId(), bookDto, id);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws CannotProceedException {
        bookService.delete(jwtService.getUserId(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/rent")
    public ResponseEntity<BookDto> rent(@PathVariable Long id) throws CannotProceedException {
        BookDto registedBook = bookService.rent(jwtService.getUserId(), id);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}/giveBack")
    public ResponseEntity<BookDto> giveBack(@PathVariable Long id) throws CannotProceedException {
        BookDto registedBook = bookService.giveBack(jwtService.getUserId(), id);
        addHateoasSelf(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        BookDto bookDto = bookService.findBookById(id).toBookDto();
        addHateoasSelf(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    private void addHateoasSelf(BookDto bookDto) {
        bookDto.add(linkTo(ApiBookController.class).slash(bookDto.getId()).withSelfRel());
    }

}
