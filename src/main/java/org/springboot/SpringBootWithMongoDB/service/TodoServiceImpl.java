package org.springboot.SpringBootWithMongoDB.service;

import org.springboot.SpringBootWithMongoDB.exception.TodoCollectionException;
import org.springboot.SpringBootWithMongoDB.model.TodoDTO;
import org.springboot.SpringBootWithMongoDB.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException {

        Optional<TodoDTO> todoDTOOptional = todoRepository.findByTodo(todoDTO.getTodo());

        if(todoDTOOptional.isPresent()) {

            throw new TodoCollectionException(TodoCollectionException.todoAlreadyExists());

        } else {

            todoDTO.setCreatedAt(new Date(System.currentTimeMillis()));
            todoDTO.setUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(todoDTO);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {

        List<TodoDTO> todoDTOS = todoRepository.findAll();

        if(todoDTOS.size() > 0) {

            return todoDTOS;

        } else {

            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {

        Optional<TodoDTO> todoDTO = todoRepository.findById(id);

        if(!todoDTO.isPresent()) {

            throw new TodoCollectionException(TodoCollectionException.notFoundException(id));

        } else {

            return todoDTO.get();
        }
    }

    @Override
    public void deleteTodoById(String id) throws TodoCollectionException {

        Optional<TodoDTO> todoDTO = todoRepository.findById(id);

        if(!todoDTO.isPresent()) {

            throw new TodoCollectionException(TodoCollectionException.notFoundException(id));

        } else {

            todoRepository.deleteById(id);
        }
    }
}
