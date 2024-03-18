package com.example.roommate.domain.model.exceptions;

public class LoginException extends RuntimeException {
    public LoginException(String cause) {
        super(cause);
    }
}
