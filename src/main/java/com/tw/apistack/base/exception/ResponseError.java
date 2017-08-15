package com.tw.apistack.base.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by jxzhong on 10/18/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "status", "title", "details"})
public class ResponseError implements Serializable {

    private final String id;

    private final String status;

    private final String title;

    private final String code;

    private final String details;

    public ResponseError(String title) {
        this(null, null, null, title, null);
    }

    public ResponseError(Integer statusCode, String title) {
        this(null, statusCode, null, title, null);
    }

    public ResponseError(String title, String details) {
        this(null, null, null, title, details);
    }

    public ResponseError(Integer statusCode, String title, String details) {
        this(null, statusCode, null, title, details);
    }

    public ResponseError(String id, Integer statusCode, String code, String title, String details) {
        this.id = id;
        this.status = statusCode == null ? null : statusCode.toString();
        this.title = title;
        this.code = code;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
