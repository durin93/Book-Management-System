package com.durin93.bookmanagement.exception;

public class JwtAuthorizationException extends RuntimeException {
    public JwtAuthorizationException() {
        super("토큰이 유효하지 않습니다. 다시 로그인을 해주세요.");
    }

}
