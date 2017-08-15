package com.tw.apistack.base.exception;

/**
 * Created by jxzhong on 12/16/16.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
