package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class ApiBookController {

    private static final Logger log = LoggerFactory.getLogger(ApiBookController.class);

    private BookService bookService;

    public ApiBookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("")
    public ResponseEntity<BookDto> regist(@RequestBody BookDto bookDto) {
        BookDto registedBook = bookService.regist(bookDto);
        return new ResponseEntity<>(registedBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.update(bookDto, id);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/rent")
    public ResponseEntity<BookDto> rent(@PathVariable Long id) {
        BookDto rentedBook = bookService.rent(id);
        return new ResponseEntity<>(rentedBook, HttpStatus.OK);
    }

    @PutMapping("{id}/giveBack")
    public ResponseEntity<BookDto> giveBack(@PathVariable Long id) {
        BookDto giveBackedBook = bookService.giveBack(id);
        return new ResponseEntity<>(giveBackedBook, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        BookDto findBook = bookService.findBookById(id).toBookDto();
        return new ResponseEntity<>(findBook, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<BookDtos> search(SearchDto searchDto) {
        BookDtos searchBook = bookService.search(searchDto);
        return new ResponseEntity<>(searchBook, HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<BookDtos> showRentBooks(@PathVariable Long id) {
        BookDtos rentBooks = bookService.findRentBooks(id);
        return new ResponseEntity<>(rentBooks, HttpStatus.OK);
    }

}
