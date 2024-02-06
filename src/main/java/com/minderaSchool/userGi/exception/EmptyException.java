package com.minderaSchool.userGi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyException extends RuntimeException {
    public EmptyException() {
        super("Username and/or password cannot be empty");
    }
}