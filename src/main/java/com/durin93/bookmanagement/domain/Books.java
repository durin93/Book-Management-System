package com.durin93.bookmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Books {

    private static final int MAX_RENT_SIZE = 3;

    @OneToMany(mappedBy = "render")
    @JsonIgnore
    @Where(clause = "is_deleted = false")
    private List<Book> rentBooks = new ArrayList<>();

    public List<Book> rentBook(Book book) {
        rentBooks.add(book);
        return rentBooks;
    }

    public List<Book> giveBackBook(Book book) {
        rentBooks.remove(book);
        return rentBooks;
    }

    public List<Book> getRentBooks() {
        return rentBooks;
    }

}
