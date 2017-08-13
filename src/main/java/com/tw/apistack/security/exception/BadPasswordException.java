package com.tw.apistack.security.exception;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String passsword) {
        super(passsword);
    }
}
