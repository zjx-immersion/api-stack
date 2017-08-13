package com.tw.apistack.core.todo;

import com.tw.apistack.core.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jxzhong on 2017/7/3.
 */
@Repository
public class DummyTodoRepository {

    private Set<Todo> todos = new HashSet<>();

    public DummyTodoRepository() {
        this.todos = new HashSet<>();
    }

    public List<Todo> getAll() {
        return todos.stream().collect(Collectors.toList());
    }

    public Optional<Todo> findById(long id) {
        return todos.stream().filter(todo -> todo.getId() == id).findFirst();
    }

    public void delete(Todo todo) {
        todos.remove(todo);
    }

    public void add(Todo todo) {
        if (todo.getId() == 0) {
            todo.setId(todos.size() + 1);
            todo.setOrder(1);
        }
        todos.add(todo);
    }

}
