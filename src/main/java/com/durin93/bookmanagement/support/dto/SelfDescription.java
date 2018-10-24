package com.durin93.bookmanagement.support.dto;

public enum SelfDescription {

    BOOKS("https://github.com/durin93/Book-Management-System/wiki/Books-API"),
    USERS("https://github.com/durin93/Book-Management-System/wiki/USERS-API"),
    HISTORIES("https://github.com/durin93/Book-Management-System/wiki/Histories-API");

    private String docs;

    SelfDescription(String docs) {
        this.docs = docs;
    }

    public String getDocs() {
        return docs;
    }


}
