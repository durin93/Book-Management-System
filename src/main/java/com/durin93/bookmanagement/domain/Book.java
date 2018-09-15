package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Book extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String author;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_book_rendor"))
    private User render;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book(Long id, String title, String author) {
        super(id);
        this.title = title;
        this.author = author;
    }

    public BookDto toBookDto() {
        return new BookDto(getId(), title, author, checkRender(), isDeleted);
    }

    public User getRender() {
        return render;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Book update(BookDto bookDto) {
        checkRentable();
        title = bookDto.getTitle();
        author = bookDto.getAuthor();
        return this;
    }

    public Book rentBy(User loginUser) {
        checkRentable();
        render = loginUser;
        render.rentBook(this);
        return this;
    }

    public void giveBackBy() {
        if (checkRender()) {
            throw new RentalException("이미 반납된 도서입니다.");
        }
        render.giveBackBook(this);
        render = null;
    }

    protected Boolean checkRentable() {
        if (!checkRender()) {
            throw new RentalException("대여중인 도서입니다.");
        }
        return true;
    }
    public Book delete() {
        checkRentable();
        isDeleted = true;
        return this;
    }

    protected Boolean checkRender() {
        return render == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (render != null ? !render.equals(book.render) : book.render != null) return false;
        return isDeleted != null ? isDeleted.equals(book.isDeleted) : book.isDeleted == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (render != null ? render.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", render=" + render +
                ", isDeleted=" + isDeleted +
                '}';
    }

}
