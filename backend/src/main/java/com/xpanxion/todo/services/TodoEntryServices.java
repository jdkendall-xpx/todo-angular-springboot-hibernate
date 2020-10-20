package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoEntryServices {
    @Autowired
    TodoRepository todoRepository;

    public List getTodos() {
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

// Return all found results
        return todoRepository.findAll(sortByCreatedAt);

    }

    public TodoEntry postTodo(TodoEntry postTodo) throws com.xpanxion.todo.exceptions.InvalidException {
        TodoEntry savedEntry = todoRepository.save(postTodo);
        return savedEntry;

    }

    public TodoEntry getTodoById(long id) throws com.xpanxion.todo.exceptions.InvalidException {


        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

// Check if we found an entry
        if (originalEntry.isPresent()) {
// Use the entry's data
            TodoEntry entryData = originalEntry.get();

// Return the to-do entry inside a 200 OK response
            return entryData;
        } else {
// Return a 404 Not Found status, since we didn't find the entry
            throw new com.xpanxion.todo.exceptions.InvalidException();
        }

    }

    public TodoEntry updateTodo(long id, TodoEntry changes) throws com.xpanxion.todo.exceptions.InvalidException {
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);

// Check if we found an entry
        if (originalEntry.isPresent()) {
// Use the entry's data
            TodoEntry entryData = originalEntry.get();

// Update the entry
            this.updateEntry(entryData, changes);
// Save the updated version to the database
            TodoEntry updatedTodo = this.todoRepository.save(entryData);

// Return the updated full entry
            return updatedTodo;
        } else {
// throwing invalid id exception
            throw new com.xpanxion.todo.exceptions.InvalidException();
        }
    }
    private void updateEntry (TodoEntry entryData, TodoEntry changes){
        if (changes.getTitle() != null) {

            entryData.setTitle(changes.getTitle());

        }
        if (changes.getDescription() != null) {

            entryData.setDescription(changes.getDescription());


        }
        if (changes.getCreatedAt() != null) {

            entryData.setCreatedAt(changes.getCreatedAt());


        }
        if (changes.getCompleted() != null) {

            entryData.setCompleted(changes.getCompleted());


        }
    }
    public void deleteTodo ( long id) throws com.xpanxion.todo.exceptions.InvalidException {
        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

// Check if we found an entry
        if (originalEntry.isPresent()) {
            // Delete the entry we found by ID

            // Return a 204 OK No Content status, we deleted it
            this.todoRepository.deleteById(id);


        } else {
            // Return a 404 Not Found status, since we didn't find the entry
            throw new com.xpanxion.todo.exceptions.InvalidException();
        }

    }}

