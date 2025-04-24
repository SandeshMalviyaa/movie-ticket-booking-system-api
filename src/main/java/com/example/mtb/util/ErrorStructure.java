package com.example.mtb.util;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorStructure<T>{
    private int errorCode;
    private String errorMessage;
    private T error;
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setError(T error) {
        this.error = error;
    }
}
