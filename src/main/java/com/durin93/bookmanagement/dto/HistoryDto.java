package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.support.domain.HistoryType;
import com.durin93.bookmanagement.support.domain.Links;
import com.durin93.bookmanagement.support.dto.SelfDescription;
import com.durin93.bookmanagement.web.ApiHistoryController;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class HistoryDto {

    private Long id;

    private Long userId;

    private Long bookId;

    private String historyType;

    private String createDate;

    @JsonUnwrapped
    private Links links = new Links();

    private String selfDescription = SelfDescription.HISTORIES.getDocs();


    public HistoryDto() {
    }

    public HistoryDto(Long id, Long userId, Long bookId, HistoryType historyType, String createDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.historyType = historyType.getName();
        this.createDate = createDate;
    }

    public String getHistoryType() {
        return historyType;
    }

    public void setHistoryType(String historyType) {
        this.historyType = historyType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public HistoryDto addLink() {
        links.add(linkTo(ApiHistoryController.class).slash(id).withSelfRel());
        return this;
    }

    public Link getLink(String rel) {
        return links.getLink(rel);
    }


    @Override
    public String toString() {
        return "HistoryDto{" +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", historyType=" + historyType +
                ", links=" + links +
                ", createDate='" + createDate + '\'' +
                '}';
    }


}
