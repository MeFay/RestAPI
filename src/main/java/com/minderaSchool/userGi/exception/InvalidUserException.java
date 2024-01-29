package com.minderaSchool.userGi.exception;

public class InvalidUserException extends RuntimeException {
    private static final String nullMessage = "Username and/or password cannot be empty";

    public InvalidUserException() {
        super(nullMessage);
    }


}