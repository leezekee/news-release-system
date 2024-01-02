package com.leezekee.exception;

public class TokenExpiredException extends RuntimeException {
    String message;
    String token;
    public TokenExpiredException(String token) {
        this.token = token;
        this.message = "Token " + token + " has expired";
    }
}