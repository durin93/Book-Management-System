package com.durin93.bookmanagement.support.domain;

public enum HistoryType {

    RENT("대여"),
    GIVEBACK("반납"),
    DELETE("삭제"),
    REGIST("등록"),
    UPDATE("수정");

    private String name;

    HistoryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
