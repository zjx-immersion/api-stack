package com.tw.apistack.endpoint.todo;

import com.tw.apistack.endpoint.todo.dto.ResourceWithUrl;
import com.tw.apistack.endpoint.todo.dto.TodoDTO;
import com.tw.apistack.repository.DummyTodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by jxzhong on 2017/7/3.
 */
@RestController
@RequestMapping(value = "/todos")
public class TodoResource {
    private DummyTodoRepository todoRepository;

    @Autowired
    public TodoResource(DummyTodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping
    public HttpEntity<Collection<ResourceWithUrl>> getAll() {
        List<ResourceWithUrl> resourceWithUrls = todoRepository.getAll().stream()
                .map(todo -> toResource(todo))
                .collect(Collectors.toList());
        return new ResponseEntity<>(resourceWithUrls, OK);
    }

    @GetMapping("/{todo-id}")
    public HttpEntity<ResourceWithUrl> getTodo(@PathVariable("todo-id") long id) {

        Optional<TodoDTO> todoOptional = todoRepository.findById(id);

        if (!todoOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return respondWithResource(todoOptional.get(), OK);
    }

    @PostMapping(headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> saveTodo(@RequestBody TodoDTO todo) {
        todo.setId(todoRepository.getAll().size() + 1);
        todoRepository.add(todo);

        return respondWithResource(todo, CREATED);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteOneTodo(@PathVariable("todo-id") long id) {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);

        if (todoOptional.isPresent()) {
            todoRepository.delete(todoOptional.get());
            return new ResponseEntity<>(OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{todo-id}", headers = {"Content-type=application/json"})
    public HttpEntity<ResourceWithUrl> updateTodo(@PathVariable("todo-id") long id, @RequestBody TodoDTO newTodo) {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);

        if (!todoOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        } else if (newTodo == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        todoRepository.delete(todoOptional.get());

        TodoDTO mergedTodo = todoOptional.get().merge(newTodo);
        todoRepository.add(mergedTodo);

        return respondWithResource(mergedTodo, OK);
    }


    private String getHref(TodoDTO todo) {
        return "";
        //        return linkTo(methodOn(this.getClass()).getTodo(todo.getId())).withSelfRel().getHref();
    }

    private ResourceWithUrl toResource(TodoDTO todo) {
        return new ResourceWithUrl(todo, getHref(todo));
    }

    private HttpEntity<ResourceWithUrl> respondWithResource(TodoDTO todo, HttpStatus statusCode) {
        ResourceWithUrl resourceWithUrl = toResource(todo);

        return new ResponseEntity<>(resourceWithUrl, statusCode);
    }
}
