package com.durin93.bookmanagement.dto;

import com.durin93.bookmanagement.support.domain.HistoryType;

public class HistoryDto {

    private Long userId;

    private Long bookId;

    private String historyType;

    private String createDate;

    public HistoryDto() {
    }

    public HistoryDto(Long userId, Long bookId, HistoryType historyType, String createDate) {
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


    @Override
    public String toString() {
        return "HistoryDto{" +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", historyType=" + historyType +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
