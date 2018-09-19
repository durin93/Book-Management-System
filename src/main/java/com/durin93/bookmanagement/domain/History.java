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

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HistoryType historyType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createDate = LocalDateTime.now();

    public History() {
    }

    public History(Book book, User loginUser, HistoryType historyType) {
        this.book = book;
        this.user = loginUser;
        this.historyType = historyType;
    }

    public Book getBook() {
        return book;
    }

    public HistoryType getHistoryType() {
        return historyType;
    }

    public User getUser() {
        return user;
    }

    public String getCreateDate() {
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public HistoryDto toHistoryDto() {
        return new HistoryDto(user.getId(), book.getId(), historyType, getCreateDate());
    }
}