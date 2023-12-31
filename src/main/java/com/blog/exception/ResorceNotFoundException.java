package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResorceNotFoundException extends RuntimeException {

    public ResorceNotFoundException(String message) {
        super(message);//it calls parent class constructor i.e RuntimeException again it calls exception it calls Throwable
    }
}
