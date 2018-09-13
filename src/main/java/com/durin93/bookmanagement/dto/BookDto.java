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
    private String content;

    private Boolean rentable = true;

    private String userId;

    @JsonUnwrapped
    private SelfDescription selfDescription = new SelfDescription();

    public BookDto(){

    }

    public BookDto(String title, String content){
        this.title = title;
        this.content = content;
    }

    public BookDto(Long id, String title, String content, Boolean rentable){
        this(title,content);
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRentable() {
        return rentable;
    }

    public void setRentable(Boolean rentable) {
        this.rentable = rentable;
    }

    public Book toBook() {
        return new Book(title, content);
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
        return content != null ? content.equals(bookDto.content) : bookDto.content == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", rentable=" + rentable +
                ", links=" + selfDescription +
                '}';
    }
}
