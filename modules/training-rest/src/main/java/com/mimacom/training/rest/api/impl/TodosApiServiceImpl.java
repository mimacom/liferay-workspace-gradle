package com.mimacom.training.rest.api.impl;

import com.mimacom.training.rest.api.TodosApi;
import com.mimacom.training.rest.model.NewTodo;
import com.mimacom.training.rest.model.Todo;
import com.mimacom.training.rest.model.Todos;
import org.osgi.service.component.annotations.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Mimacom Training
 *
 */
@Component(
        immediate = true
)
public class TodosApiServiceImpl implements TodosApi {

    private Todos todos = new Todos();
    private AtomicLong count = new AtomicLong();

    @Override
    public void createTodo(NewTodo todo) {

        this.todos.add(
                new Todo()
                        .id(count.incrementAndGet())
                        .title(todo.getTitle())
                        .description(todo.getDescription())
                        .tag(todo.getTag())
        );
    }

    @Override
    public Todo getTodoById(Long todoId) {
        return this.todos.stream()
                .filter(todo -> todoId.equals(todo.getId()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public Todos listTodos(Integer limit) {
        return todos;
    }
}

