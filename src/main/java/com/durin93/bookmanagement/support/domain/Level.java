package com.durin93.bookmanagement.support.domain;

public enum Level {

    MANAGER("관리자"),
    USER("사용자");

    private String name;

    Level(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isManager() {
        return name.equals("관리자");
    }
}
