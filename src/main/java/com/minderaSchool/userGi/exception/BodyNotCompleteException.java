package com.minderaSchool.userGi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BodyNotCompleteException extends RuntimeException {
    public BodyNotCompleteException() {
        super("Username and/or password cannot be empty");
    }
}