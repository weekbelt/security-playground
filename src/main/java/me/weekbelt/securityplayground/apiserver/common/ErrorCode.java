package me.weekbelt.securityplayground.apiserver.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "token-001", "Invalid Token"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "token-002", "Token is expired")
    ;

    private HttpStatus httpStatus;

    private String code;

    private String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
