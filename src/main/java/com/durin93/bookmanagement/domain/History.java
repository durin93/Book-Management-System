package com.durin93.bookmanagement.domain;

import com.durin93.bookmanagement.dto.HistoryDto;
import com.durin93.bookmanagement.support.domain.HistoryType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class History {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long bookId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HistoryType historyType;

    @Column
    private Long userId;

    @Column
    private LocalDateTime createDate = LocalDateTime.now();

    public History() {
    }

    public History(Long bookId, Long userId, HistoryType historyType) {
        this.bookId = bookId;
        this.userId = userId;
        this.historyType = historyType;
    }


    public HistoryType getHistoryType() {
        return historyType;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreateDate() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public HistoryDto toHistoryDto() {
        HistoryDto historyDto = new HistoryDto(id, userId, bookId, historyType, getCreateDate());
        return historyDto.addLink();
    }
}
