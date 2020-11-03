package com.xpanxion.todo.controllers;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.exceptions.ModifyTodoValidationException;
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.services.TodoEntryService;
import com.xpanxion.todo.validators.ModifyTodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoEntryService todoEntryService;

    @Autowired
    ModifyTodoValidator modifyTodoValidator;

    @GetMapping("/todos")
    public ResponseEntity<List<TodoEntry>> getAllTodos() {
        // Decide how we want to sort our database results -- here we want to go by createdAt column in descending order
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

        // Return all found results
        List<TodoEntry> results = todoRepository.findAll(sortByCreatedAt);
        return ResponseEntity.ok().body(results);
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoEntry> createTodo(@RequestBody TodoEntry todo) {
        // Save our entry to the database and return the saved entry
        TodoEntry savedEntry = todoRepository.save(todo);
        return ResponseEntity.ok().body(savedEntry);
    }

    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            // Get our to-do entry from the database
            Optional<TodoEntry> originalEntry = todoRepository.findById(id);

            // Check if we found an entry
            if (originalEntry.isPresent()) {
                // Use the entry's data
                TodoEntry entryData = originalEntry.get();

                // Return the to-do entry inside a 200 OK response
                return ResponseEntity.ok().body(entryData);
            } else {
                // Return a 404 Not Found status, since we didn't find the entry
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/todos/{id}")
    public ResponseEntity<TodoEntry> updateTodo(@PathVariable("id") String idString, @RequestBody TodoEntry todoChanges) {
        try {
            TodoEntryChanges validatedChanges = this.modifyTodoValidator.validate(idString, todoChanges);

            TodoEntry result = this.todoEntryService.updateTodo(validatedChanges);

            return ResponseEntity.ok().body(result);
        } catch(ModifyTodoValidationException ex) {
            // Return a 400 Bad Request response, we did not pass validation
            System.out.println(ex.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (InvalidIdException ex) {
            // Return a 404 Not Found response, we did not find a valid entry for the ID
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);
            Optional<TodoEntry> originalEntry = todoRepository.findById(id);

            // Check if we found an entry
            if (originalEntry.isPresent()) {
                // Delete the entry we found by ID
                todoRepository.deleteById(id);

                // Return a 204 OK No Content status, we deleted it
                return ResponseEntity.noContent().build();
            } else {
                // Return a 404 Not Found status, since we didn't find the entry
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        }
    }
}