package com.durin93.bookmanagement.dto;

public class SearchDto {

    private String searchType;

    private String content;

    public SearchDto() {
    }

    public SearchDto(String searchType, String content) {
        this.searchType = searchType;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public boolean isSearchTypeTitle() {
        return searchType.equals("title");
    }

    public boolean isSearchTypeAuthor() {
        return searchType.equals("author");
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "searchType='" + searchType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
