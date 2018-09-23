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
        return ResponseEntity.status(HttpStatus.CREATED).body(registedBook);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.update(bookDto, id);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/rent")
    public ResponseEntity<BookDto> rent(@PathVariable Long id) {
        BookDto rentedBook = bookService.rent(id);
        return ResponseEntity.ok(rentedBook);
    }

    @PutMapping("{id}/giveBack")
    public ResponseEntity<BookDto> giveBack(@PathVariable Long id) {
        BookDto giveBackedBook = bookService.giveBack(id);
        return ResponseEntity.ok(giveBackedBook);
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        BookDto findBook = bookService.findBookById(id).toBookDto();
        return ResponseEntity.ok(findBook);
    }

    @GetMapping("")
    public ResponseEntity<BookDtos> search(SearchDto searchDto) {
        BookDtos searchBook = bookService.search(searchDto);
        return ResponseEntity.ok(searchBook);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<BookDtos> showRentBooks(@PathVariable Long id) {
        BookDtos rentBooks = bookService.findRentBooks(id);
        return ResponseEntity.ok(rentBooks);
    }

}
