package com.github.rafaelmdb.domain.exception;

public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String message) {
        super(message);
    }

    public RegraNegocioException() {
    }
}
