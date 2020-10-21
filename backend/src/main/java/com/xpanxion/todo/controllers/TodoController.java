package com.xpanxion.todo.controllers;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.services.TodoEntryService;
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

    @GetMapping("/todos")
    public ResponseEntity<List<TodoEntry>> getAllTodos() {
        // Decide how we want to sort our database results -- here we want to go by createdAt column in descending order
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

        // Return all found results
        this.todoEntryService.getAllTodos();
        List<TodoEntry> results = todoRepository.findAll(sortByCreatedAt);
        return ResponseEntity.ok().body(results);

    }

    @PostMapping("/todos")
    public ResponseEntity<TodoEntry> createTodo(@RequestBody TodoEntry newEntry) {
        // Save our entry to the database and return the saved entry
        TodoEntry savedEntry = this.todoEntryService.createTodo(newEntry);
        return ResponseEntity.ok().body(savedEntry);
    }

    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            this.todoEntryService.getTodoById(id);
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
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            TodoEntry result = this.todoEntryService.updateTodo(id, todoChanges);

            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch (InvalidIdException ex) {
            // Return a 404 Not found response, we did not find a valid entry ID
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            this.todoEntryService.deleteTodo(id);
            // return a 284 ok no content status it was deleted
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch (InvalidIdException ex) {
            // return 404 response as valid ID was not found in database
            return ResponseEntity.notFound().build();
        }
    }
}