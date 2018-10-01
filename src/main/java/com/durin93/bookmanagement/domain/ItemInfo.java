package com.durin93.bookmanagement.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Embeddable
public class ItemInfo {

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false)
    private int pageNumber;

    @Column(nullable = false)
    private int weight;

    public ItemInfo() {
    }

    public ItemInfo(LocalDate releaseDate, int pageNumber, int weight) {
        this.releaseDate = releaseDate;
        this.pageNumber = pageNumber;
        this.weight = weight;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getWeight() {
        return weight;
    }

    public String getItemInfo() {
        return releaseDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + pageNumber + "쪽" + weight + "g";
    }

}
