package com.tw.apistack.endpoint.todo.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by jxzhong on 2017/7/3.
 */
@XmlRootElement
public class ResourceWithUrl<T> {

    private T content;
    private String url;

    public ResourceWithUrl() {
    }

    public ResourceWithUrl(T content, String url) {
        this.content = content;
        this.url = url;
    }

    @JsonUnwrapped
    @XmlAnyElement
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
