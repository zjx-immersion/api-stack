package com.tw.apistack.endpoint.todo.dto;

/**
 * Created by jxzhong on 2017/7/3.
 */
public class TodoDTO {

    private int id;
    private String title;
    private Boolean completed;
    private Integer order;

    public TodoDTO() {
    }

    public TodoDTO(String title) {
        this.title = title;
    }

    public TodoDTO(String title, Boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public TodoDTO(int id, String title, Boolean completed, Integer order) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TodoDTO todo = (TodoDTO) o;

        if (id != todo.id) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public boolean isCompleted() {
        return nonNull(completed, false);
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getOrder() {
        return nonNull(order, 0);
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public TodoDTO merge(TodoDTO newTodo) {
        return new TodoDTO(id,
                nonNull(newTodo.title, title),
                nonNull(newTodo.completed, completed),
                nonNull(newTodo.order, order));
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private <T> T nonNull(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}