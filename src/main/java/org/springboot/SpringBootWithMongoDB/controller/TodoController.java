package org.springboot.SpringBootWithMongoDB.controller;

import org.springboot.SpringBootWithMongoDB.exception.TodoCollectionException;
import org.springboot.SpringBootWithMongoDB.model.TodoDTO;
import org.springboot.SpringBootWithMongoDB.repository.TodoRepository;
import org.springboot.SpringBootWithMongoDB.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {

        List<TodoDTO> todoDTOS = todoService.getAllTodos();

        return new ResponseEntity<>(todoDTOS, todoDTOS.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {

        try {

            todoService.createTodo(todoDTO);

            return new ResponseEntity<>(todoDTO, HttpStatus.OK);

        } catch (ConstraintViolationException e) {

            System.out.println(e.getMessage());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);

        } catch (TodoCollectionException e) {

            System.out.println(e.getMessage());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {

        try {

            return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-todo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id, @RequestBody TodoDTO todoDTO) {

        Optional<TodoDTO> todo = todoRepository.findById(id);

        if (todo.isPresent()) {

            TodoDTO savedTodoDTO = todo.get();

            savedTodoDTO.setTodo(todoDTO.getTodo() != null ? todoDTO.getTodo() : savedTodoDTO.getTodo());
            savedTodoDTO.setDescription(todoDTO.getDescription() != null ? todoDTO.getDescription() : savedTodoDTO.getDescription());
            savedTodoDTO.setCompleted(todoDTO.getCompleted() != null ? todoDTO.getCompleted() : savedTodoDTO.getCompleted());
            savedTodoDTO.setCreatedAt(todoDTO.getCreatedAt() != null ? todoDTO.getCreatedAt() : savedTodoDTO.getCreatedAt());
            savedTodoDTO.setUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(savedTodoDTO);

            return new ResponseEntity<>(savedTodoDTO, HttpStatus.OK);

        } else {

            return new ResponseEntity<>("Todo not found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-todo/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id) {

        try {

            todoService.deleteTodoById(id);

            return new ResponseEntity<>("Successfully todo with id " + id + " has been deleted!!", HttpStatus.OK);

        } catch (TodoCollectionException e) {

            System.out.println(e.getMessage());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
