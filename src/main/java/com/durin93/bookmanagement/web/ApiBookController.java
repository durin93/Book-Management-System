package com.durin93.bookmanagement.web;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.dto.BookDtos;
import com.durin93.bookmanagement.dto.SearchDto;
import com.durin93.bookmanagement.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class ApiBookController {

    private BookService bookService;

    public ApiBookController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("")
    public ResponseEntity<BookDto> regist(@Valid @RequestBody BookDto bookDto, BindingResult error) {
        if (error.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.regist(bookDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id, @Valid @RequestBody BookDto bookDto, BindingResult error) {
        if (error.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookService.update(bookDto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/rent")
    public ResponseEntity<BookDto> rent(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.rent(id));
    }

    @PutMapping("{id}/giveBack")
    public ResponseEntity<BookDto> giveBack(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.giveBack(id));
    }


    @GetMapping("{id}")
    public ResponseEntity<BookDto> show(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id).toBookDto());
    }

    @GetMapping("")
    public ResponseEntity<BookDtos> search(SearchDto searchDto) {
        return ResponseEntity.ok(bookService.search(searchDto));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<BookDtos> showRentBooks(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findRentBooks(id));
    }

}
