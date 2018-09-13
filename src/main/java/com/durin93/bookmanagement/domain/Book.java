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
    private String content;

    @ManyToOne
    @JoinColumn(name = "render_id")
    private User render;

    @Column(nullable = false)
    private Boolean rentable = true;

    public Book() {

    }

    public Book(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public User getRender() {
        return render;
    }

    public Boolean getRentable() {
        return rentable;
    }


    public BookDto toBookDto() {
        return new BookDto(getId(), title, content, rentable);
    }


    public Book update(BookDto bookDto) {
        title = Optional.ofNullable(bookDto.getTitle()).orElse(title);
        content = Optional.ofNullable(bookDto.getContent()).orElse(content);
        return this;
    }

    public void rent(User loginUser) throws CannotProceedException {
        if (!rentable) {
            throw new CannotProceedException("이미 대여중인 도서입니다.");
        }
        this.rentable = false;
        this.render = loginUser;
    }

    public void giveBack(User loginUser) throws CannotProceedException {
        if (rentable) {
            throw new CannotProceedException("이미 반납된 도서입니다.");
        }
        this.rentable = true;
        this.render = null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (content != null ? !content.equals(book.content) : book.content != null) return false;
        if (render != null ? !render.equals(book.render) : book.render != null) return false;
        return rentable != null ? rentable.equals(book.rentable) : book.rentable == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (render != null ? render.hashCode() : 0);
        result = 31 * result + (rentable != null ? rentable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", render=" + render +
                ", rentable=" + rentable +
                '}';
    }


}
