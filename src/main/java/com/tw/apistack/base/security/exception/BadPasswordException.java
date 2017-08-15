package com.tw.apistack.base.security.exception;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String passsword) {
        super(passsword);
    }
}
