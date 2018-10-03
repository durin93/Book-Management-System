package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDtos {

    private List<BookDto> books;

    public BookDtos() {

    }

    public BookDtos(List<BookDto> books) {
        this.books = books;
    }

    public static BookDtos of(List<Book> books) {
        List<BookDto> bookDtos = books.stream().map(b -> b.toBookDto()).collect(Collectors.toList());
        return new BookDtos(bookDtos);
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    public boolean hasBook(BookDto book) {
        return books.contains(book);
    }
}
