package com.example.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class JwtException extends RuntimeException {

    private final String message;

    public JwtException(String message) {
        super(message);
        this.message = message;
    }
}
