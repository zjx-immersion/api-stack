package com.tw.apistack.endpoint.todo;

import com.tw.apistack.endpoint.todo.dto.ResourceWithUrl;
import com.tw.apistack.endpoint.todo.dto.TodoDTO;
import com.tw.apistack.repository.DummyTodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by jxzhong on 2017/7/3.
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoResourceTest {


    @Mock
    DummyTodoRepository todoRepository;

    @Test
    public void should_get_all_todo_list_and_status_OK_when_call_get_todos() throws Exception {
        //given

        when(todoRepository.getAll()).thenReturn(asList(
                new TodoDTO(1, "test-A", false, 1),
                new TodoDTO(2, "test-B", false, 1)));
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<Collection<ResourceWithUrl>> allTodosRes = helloResource.getAll();

        //then
        assertThat(allTodosRes.getBody().size()).isEqualTo(2);
        assertThat(((ResponseEntity) allTodosRes).getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void should_get_todo_with_id_1_and_status_OK_when_call_get_todo_with_param_1() throws Exception {
        //given

        when(todoRepository.findById(1)).thenReturn(
                Optional.of(new TodoDTO(1, "test-A", false, 1)));
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.getTodo(1);

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(todoRes.getBody().getContent().getClass().getTypeName()).isEqualTo(TodoDTO.class.getTypeName());
        assertThat(((TodoDTO) todoRes.getBody().getContent()).getId()).isEqualTo(1);
    }


    @Test
    public void should_not_get_todo_with_id_3_and_status_404_when_call_get_todo_with_param_3() throws Exception {
        //given

        when(todoRepository.findById(3)).thenReturn(Optional.ofNullable(null));
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.getTodo(3);

        //then
        assertThat(todoRes.getBody()).isNull();
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void should_add_todo_with_status_201_when_call_save_todo() throws Exception {
        //given

        TodoDTO todoDTO = new TodoDTO();
        doNothing().when(todoRepository).add(todoDTO);
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.saveTodo(todoDTO);

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


    @Test
    public void should_delete_todo_with_status_200_when_call_delete_todo() throws Exception {
        //given

        TodoDTO todo = new TodoDTO(1, "test-A", false, 1);
        when(todoRepository.findById(1)).thenReturn(
                Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.deleteOneTodo(1);

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void should_not_delete_todo_with_status_404_when_call_delete_a_no_exist_todo() throws Exception {
        //given

        when(todoRepository.findById(3)).thenReturn(Optional.ofNullable(null));
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.deleteOneTodo(3);

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void should_not_update_todo_with_status_404_when_call_update_a_no_exist_todo() throws Exception {
        //given

        when(todoRepository.findById(3)).thenReturn(Optional.ofNullable(null));
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.updateTodo(3, new TodoDTO());

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void should_not_update_todo_with_status_400_when_call_update_a_empty_todo() throws Exception {
        //given

        TodoDTO todo = new TodoDTO(1, "test-A", false, 1);
        when(todoRepository.findById(1)).thenReturn(
                Optional.of(todo));
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.updateTodo(1, null);

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void should_update_todo_with_status_200_when_call_update_a_exist_todo() throws Exception {
        //given

        TodoDTO todo = new TodoDTO(1, "test-A", false, 1);
        TodoDTO newTodo = new TodoDTO(1, "test-A", true, 1);
        when(todoRepository.findById(1)).thenReturn(
                Optional.of(todo));
        doNothing().when(todoRepository).delete(todo);
        doNothing().when(todoRepository).add(todo);
        TodoResource helloResource = new TodoResource(todoRepository);

        //when
        HttpEntity<ResourceWithUrl> todoRes = helloResource.updateTodo(1, newTodo);

        //then
        assertThat(((ResponseEntity) todoRes).getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat((((TodoDTO) todoRes.getBody().getContent())).isCompleted()).isEqualTo(newTodo.isCompleted());

    }

}