package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDtos {

    private List<BookDto> books;

    public BookDtos() {

    }

    public BookDtos(List<BookDto> books) {
        this.books = books;
    }

    public static BookDtos of(List<Book> books) {
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            bookDtos.add(book.toBookDto());
        }
        return new BookDtos(bookDtos);
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }

    public Boolean hasBook(BookDto book) {

        return books.contains(book);
    }
}
