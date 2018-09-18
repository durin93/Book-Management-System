package com.durin93.bookmanagement.support.domain;

public enum ErrorManager {
    ALREADY_RENT("대여중인 도서입니다."),
    ALREADY_GIVEBACK("이미 반납된 도서입니다."),
    NOT_EXIST_BOOK("존재하지 않는 도서입니다."),
    WRONG_PASSWORD("비밀번호가 틀렸습니다."),
    NO_MANAGER("관리자만 접근 가능합니다."),
    EXIST_ID("이미 존재하는 아이디입니다."),
    NOT_EXIST_ID("존재하지 않는 아이디입니다.");

    private String message;

    ErrorManager() {
    }

    ErrorManager(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
