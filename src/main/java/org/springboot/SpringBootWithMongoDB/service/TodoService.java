package org.springboot.SpringBootWithMongoDB.service;

import org.springboot.SpringBootWithMongoDB.exception.TodoCollectionException;
import org.springboot.SpringBootWithMongoDB.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {

    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException;
    public List<TodoDTO> getAllTodos();
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException;
    public void deleteTodoById(String id) throws TodoCollectionException;
}
