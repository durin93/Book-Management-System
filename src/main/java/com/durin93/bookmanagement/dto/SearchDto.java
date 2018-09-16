package com.durin93.bookmanagement.dto;

public class SearchDto {

    private String label;

    private String content;

    public SearchDto() {
    }

    public SearchDto(String label, String content) {
        this.label = label;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLabelTitle() {
        return  label.equals("title");
    }

    public boolean isLabelAuthor() {
        return  label.equals("author");
    }

    @Override
    public String toString() {
        return "SearchDto{" +
                "label='" + label + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
