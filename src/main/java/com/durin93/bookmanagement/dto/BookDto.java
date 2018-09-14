package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.support.domain.SelfDescription;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;

import javax.validation.constraints.Size;

public class BookDto{

    private Long id;

    @Size(min = 3, max = 20)
    private String title;

    @Size(min = 3)
    private String author;

    private Boolean rentable = true;

    @JsonUnwrapped
    private SelfDescription selfDescription = new SelfDescription();

    public BookDto(){

    }

    public BookDto(String title, String author){
        this.title = title;
        this.author = author;
    }

    public BookDto(Long id, String title, String author, Boolean rentable){
        this(title,author);
        this.id = id;
        this.rentable = rentable;
    }

    public SelfDescription getSelfDescription() {
        return selfDescription;
    }

    public Link getLink(String rel){
        return selfDescription.getLink(rel);
    }

    public void setSelfDescription(SelfDescription selfDescription) {
        this.selfDescription = selfDescription;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getRentable() {
        return rentable;
    }

    public void setRentable(Boolean rentable) {
        this.rentable = rentable;
    }

    public Book toBook() {
        return new Book(title, author);
    }

    public void add(Link link){
        selfDescription.add(link);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookDto bookDto = (BookDto) o;

        if (title != null ? !title.equals(bookDto.title) : bookDto.title != null) return false;
        return author != null ? author.equals(bookDto.author) : bookDto.author == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rentable=" + rentable +
                ", links=" + selfDescription +
                '}';
    }
}
