package com.xpanxion.todo.controllers;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.services.TodoEntryService;
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
    TodoEntryService todoEntryService;

    @GetMapping("/todos")
    public ResponseEntity<List<TodoEntry>> getAllTodos() {
        try {
            List result = this.todoEntryService.getTodos();

            return ResponseEntity.ok().body(result);

        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch (InvalidIdException ex) {
            //return a 404 not found response, we did not find a valid entry for the ID
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoEntry> createTodo(@RequestBody TodoEntry newEntry) {
       TodoEntry savedEntry = this.todoEntryService.createTodo(newEntry);

       return ResponseEntity.ok().body(savedEntry);
    }

    @GetMapping(value = "/todos/{id}")
    public ResponseEntity<TodoEntry> getTodoById(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            TodoEntry result = this.todoEntryService.getTodo(id);
            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch (InvalidIdException ex) {
            //return a 404 not found response, we did not find a valid entry for the ID
           return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/todos/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable("id") String idString, @RequestBody TodoEntry todoChanges) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

            TodoEntry result = this.todoEntryService.updateTodo(id, todoChanges);

            return ResponseEntity.ok().body(result);
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        } catch(InvalidIdException ex) {
            //return a 404 not Found response, we did not find a valid entry for the ID
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String idString) {
        try {
            // Turn our string into an ID number
            long id = Long.parseLong(idString);

           this.todoEntryService.deleteTodo(id);
           //Return a 204 OK no content status, we deleted it
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            return ResponseEntity.badRequest().build();
        }catch(InvalidIdException ex) {
            //return a 404 not Found response, we did not find a valid entry for the ID
            return ResponseEntity.notFound().build();
        }
    }
}