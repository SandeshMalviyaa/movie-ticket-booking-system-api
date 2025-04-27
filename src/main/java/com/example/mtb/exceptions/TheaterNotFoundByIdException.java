package com.example.mtb.exceptions;

public class TheaterNotFoundByIdException extends RuntimeException {
    private String message;

    public TheaterNotFoundByIdException(String message) {
        this.message = message;
    }
}
