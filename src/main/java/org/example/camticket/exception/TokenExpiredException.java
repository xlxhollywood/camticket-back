package org.example.camticket.exception;

public class TokenExpiredException  extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
