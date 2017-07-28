package com.tw.apistack.security;

public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String passsword) {
        super(passsword);
    }
}
