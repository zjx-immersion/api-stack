package com.tw.apistack.base.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxzhong on 10/18/16.
 */
public abstract class ResponseBase {

    private List<ResponseError> errors;

    public List<ResponseError> getErrors() {
        return errors;
    }

    public void addError(String title) {
        initErrors();
        errors.add(new ResponseError(title));
    }


    public void addError(Integer statusCode, String title) {
        initErrors();
        errors.add(new ResponseError(statusCode, title));
    }


    public void addError(String title, String details) {
        initErrors();
        errors.add(new ResponseError(title, details));
    }


    public void addError(Integer statusCode, String title, String details) {
        initErrors();
        errors.add(new ResponseError(statusCode, title, details));
    }


    public void addError(String id, Integer statusCode, String code, String title, String details) {
        initErrors();
        errors.add(new ResponseError(id, statusCode, code, title, details));
    }

    private void initErrors() {
        if (errors == null) {
            errors = new ArrayList<>();
        }
    }

}
