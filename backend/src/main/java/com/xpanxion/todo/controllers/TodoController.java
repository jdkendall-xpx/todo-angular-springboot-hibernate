package com.xpanxion.todo.controllers;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.exceptions.InvalidException;
import com.xpanxion.todo.services.TodoEntryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoEntryServices todoEntryService;

    @GetMapping("/todos")
    public ResponseEntity<List<TodoEntry>> getAllTodos() {
        // Decide how we want to sort our database results -- here we want to go by createdAt column in descending order

        List<TodoEntry> results = todoEntryService.getTodos();
        return ResponseEntity.ok().body(results);

    }

    @PostMapping("/todos")
    public ResponseEntity<TodoEntry> createTodo(@RequestBody TodoEntry todoPostData) throws InvalidException {
        // Save our entry to the database and return the saved entry
        TodoEntry savedEntry = todoEntryService.postTodo(todoPostData);
        return ResponseEntity.ok().body(savedEntry);
    }

    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            // Get our to-do entry from the database
            TodoEntry result = this.todoEntryService.getTodoById(id);
            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        }
        catch (InvalidException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/todos/{id}")
    public ResponseEntity<TodoEntry> updateTodo(@PathVariable("id") String idString, @RequestBody TodoEntry todo) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            TodoEntry result = this.todoEntryService.updateTodo(id, todo);
            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch (InvalidException ex) {
            //return a 404 not found response, did not find a valid entry for id
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String idString) throws InvalidException{
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);
            this.todoEntryService.deleteTodo(id);

            return ResponseEntity.noContent().build();

        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch (InvalidException ex) {
            return ResponseEntity.notFound().build();

        }
    }
}