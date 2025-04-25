package com.example.mtb.exceptions;

public class UserNotFoundByEmailException extends RuntimeException{
    private String message;

    public UserNotFoundByEmailException(String message) {
        this.message = message;
    }
}
