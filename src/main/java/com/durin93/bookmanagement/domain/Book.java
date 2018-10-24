package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.BookDto;
import com.durin93.bookmanagement.exception.RentalException;
import com.durin93.bookmanagement.support.domain.AbstractEntity;
import com.durin93.bookmanagement.support.exception.ErrorManager;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Optional;

@Entity
public class Book extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String title;

    @Size(min = 2, max = 10)
    @Column(nullable = false)
    private String author;

    @Embedded
    private ItemInfo itemInfo;

    @ManyToOne
    @JoinColumn(name = "render_id")
    private User render;

    @Column(nullable = false)
    private Boolean isDeleted = false;


    public Book() {

    }

    public Book(String title, String author, ItemInfo itemInfo) {
        this.title = title;
        this.author = author;
        this.itemInfo = itemInfo;
    }

    public BookDto toBookDto() {
        BookDto bookDto = new BookDto(getId(), title, author, !existRender(), isDeleted, itemInfo);
        return bookDto.addLink(Optional.ofNullable(render));
    }

    public User getRender() {
        return render;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public Book update(User loginUser, BookDto bookDto) {
        loginUser.checkManager();
        checkRentable();
        title = bookDto.getTitle();
        author = bookDto.getAuthor();
        itemInfo = bookDto.convertItemInfo();
        return this;
    }

    public Book rentBy(User loginUser) {
        checkRentable();
        render = loginUser;
        return this;
    }

    public void giveBack() {
        if (!existRender()) {
            throw new RentalException(ErrorManager.ALREADY_GIVEBACK);
        }
        render = null;
    }

    public boolean checkRentable() {
        if (existRender()) {
            throw new RentalException(ErrorManager.ALREADY_RENT);
        }
        return true;
    }

    public Book delete(User loginUser) {
        loginUser.checkManager();
        checkRentable();
        isDeleted = true;
        return this;
    }

    public Boolean existRender() {
        return Optional.ofNullable(render).isPresent();
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
