package org.springboot.SpringBootWithMongoDB.repository;

import org.springboot.SpringBootWithMongoDB.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

    @Query("{'todo': ?0}") //0 -> Parameter index starts from 0th index. So, 0 index is the first parameter in the method.
    public Optional<TodoDTO> findByTodo(String todo);
}
