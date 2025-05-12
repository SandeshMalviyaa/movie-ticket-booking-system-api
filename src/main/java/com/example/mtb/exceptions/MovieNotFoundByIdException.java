package com.example.mtb.exceptions;

public class MovieNotFoundByIdException extends RuntimeException {
    private String message;

    public MovieNotFoundByIdException(String message) {
        this.message = message;
    }
}
