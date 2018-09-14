package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.support.domain.AbstractEntity;

import javax.naming.CannotProceedException;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Optional;

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

    public BookDto toBookDto() {
        return new BookDto(getId(), title, author, isRentable());
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public User getRender() {
        return render;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Book update(BookDto bookDto) {
        title = Optional.ofNullable(bookDto.getTitle()).orElse(title);
        author = Optional.ofNullable(bookDto.getAuthor()).orElse(author);
        return this;
    }

    public void rent(User loginUser) throws CannotProceedException {
        if (!isRentable()) {
            throw new CannotProceedException("이미 대여중인 도서입니다.");
        }
        this.render = loginUser;
        render.rentBook(this);
    }

    public Boolean isRentable() {
        return render == null;
    }


    public void giveBack() throws CannotProceedException {
        if (isRentable()) {
            throw new CannotProceedException("이미 반납된 도서입니다.");
        }
        this.render = null;
        render.giveBackBook(this);
    }

    public void delete() throws CannotProceedException {
        if(!isRentable()){
            throw new CannotProceedException("대여 중인 도서입니다.");
        }
        isDeleted = true;
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
