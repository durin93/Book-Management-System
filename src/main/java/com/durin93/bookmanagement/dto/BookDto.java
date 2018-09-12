package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.User;
import org.springframework.hateoas.Link;

import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class BookDto {

    private Long id;

    @Size(min = 3, max = 20)
    private String title;

    @Size(min = 3)
    private String content;

    private Boolean rentable = true;

    private List<Link> links = new ArrayList<>();


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

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Book toBook() {
        return new Book(title, content);
    }


    public void add(Link link) {
        links.add(link);
    }

    public Link getLink(String rel) {
        for (Link link : links) {
            if (link.getRel().equals(rel)) {
                return link;
            }
        }
        return null;
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
                ", links=" + links +
                '}';
    }
}
