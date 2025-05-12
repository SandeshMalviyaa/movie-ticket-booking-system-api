package com.example.mtb.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    private String message;

    public UsernameNotFoundException(String message) {
        this.message = message;
    }
}
