package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.domain.Book;
import com.durin93.bookmanagement.domain.ItemInfo;
import com.durin93.bookmanagement.domain.User;
import com.durin93.bookmanagement.support.domain.SelfDescription;
import com.durin93.bookmanagement.web.ApiBookController;
import com.durin93.bookmanagement.web.ApiUserController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class BookDto {

    private Long id;

    @Size(min = 3, max = 20)
    private String title;

    @Size(min = 3)
    private String author;

    private Boolean rentable = true;

    private Boolean isDeleted = false;

    @JsonUnwrapped
    private SelfDescription selfDescription = new SelfDescription();

    @NotBlank
    private LocalDate releaseDate;

    @NotBlank
    @Positive
    private int pageNumber;

    @NotBlank
    @Positive
    private int weight;

    public BookDto() {

    }

    public BookDto(String title, String author, LocalDate releaseDate, int pageNumber, int weight) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.pageNumber = pageNumber;
        this.weight = weight;
    }

    public BookDto(Long id, String title, String author, Boolean rentable, Boolean isDeleted, ItemInfo itemInfo) {
        this(title, author, itemInfo.getReleaseDate(), itemInfo.getPageNumber(), itemInfo.getWeight());
        this.id = id;
        this.rentable = rentable;
        this.isDeleted = isDeleted;
    }


    public SelfDescription getSelfDescription() {
        return selfDescription;
    }

    public Link getLink(String rel) {
        return selfDescription.getLink(rel);
    }

    public void setSelfDescription(SelfDescription selfDescription) {
        this.selfDescription = selfDescription;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getRentable() {
        return rentable;
    }

    public void setRentable(Boolean rentable) {
        this.rentable = rentable;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Book toBook() {
        return new Book(title, author, convertItemInfo());
    }

    public BookDto addSelfDescription(User render) {
        selfDescription.add(linkTo(ApiBookController.class).slash(id).withSelfRel());
        if (render != null) {
            selfDescription.add(linkTo(ApiUserController.class).slash(render.getId()).withRel("render"));
        }
        return this;
    }

    public ItemInfo convertItemInfo() {
        return new ItemInfo(releaseDate, pageNumber, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookDto bookDto = (BookDto) o;

        if (pageNumber != bookDto.pageNumber) return false;
        if (weight != bookDto.weight) return false;
        if (title != null ? !title.equals(bookDto.title) : bookDto.title != null) return false;
        if (author != null ? !author.equals(bookDto.author) : bookDto.author != null) return false;
        if (rentable != null ? !rentable.equals(bookDto.rentable) : bookDto.rentable != null) return false;
        if (isDeleted != null ? !isDeleted.equals(bookDto.isDeleted) : bookDto.isDeleted != null) return false;
        if (selfDescription != null ? !selfDescription.equals(bookDto.selfDescription) : bookDto.selfDescription != null)
            return false;
        return releaseDate != null ? releaseDate.equals(bookDto.releaseDate) : bookDto.releaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (rentable != null ? rentable.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (selfDescription != null ? selfDescription.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + pageNumber;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rentable=" + rentable +
                ", isDeleted=" + isDeleted +
                ", links=" + selfDescription +
                '}';
    }
}
