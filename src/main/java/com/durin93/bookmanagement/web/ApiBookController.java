package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/books")
public class ApiBookController {

    private static final Logger log = LoggerFactory.getLogger(ApiBookController.class);

    private BookService bookService;

    @Autowired
    public ApiBookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("")
    public ResponseEntity<BookDto> regist(@RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.regist(bookDto);
        addSelfDescription(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.update(bookDto, id);
        addSelfDescription(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        BookDto deletedBook = bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/rent")
    public ResponseEntity<BookDto> rent(@PathVariable Long id){
        BookDto registedBook = bookService.rent(id);
        addSelfDescription(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.OK);
    }

    @PutMapping("{id}/giveBack")
    public ResponseEntity<BookDto> giveBack(@PathVariable Long id){
        BookDto registedBook = bookService.giveBack(id);
        addSelfDescription(registedBook);
        return new ResponseEntity<>(registedBook, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        BookDto bookDto = bookService.findBookById(id).toBookDto();
        addSelfDescription(bookDto);
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @GetMapping("/mybooks")
    public ResponseEntity<List<BookDto>> showRentBooks() {
        List<BookDto> bookDto = bookService.findAllBook();
        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    private void addSelfDescription(BookDto bookDto) {
        bookDto.addLink(linkTo(ApiBookController.class).slash(bookDto.getId()).withSelfRel());
    }


}
