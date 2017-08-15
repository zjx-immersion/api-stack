package com.tw.apistack.base.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Created by jxzhong on 12/16/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO extends ResponseBase  {
//    public ErrorResponseDTO() {
//    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
