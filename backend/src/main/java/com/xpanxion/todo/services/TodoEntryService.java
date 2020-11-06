package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.InvalidException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    //get service method
    public List getTodos() {
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

        // Return all found results
        return todoRepository.findAll(sortByCreatedAt);

    }
    //post by id service method
    public TodoEntry postTodo(TodoEntry todo) throws InvalidException {
        TodoEntry savedEntry = todoRepository.save(todo);
        return savedEntry;

    }
    //get by id service method
    public TodoEntry getTodoById(long id) throws InvalidException {


        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();

            // Return the to-do entry inside a 200 OK response
            return entryData;
        } else {
            // Return a 404 Not Found status, since we didn't find the entry
            throw new InvalidException();
        }

    }
    //update service method

    public TodoEntry updateTodo(TodoEntryChanges entryChanges) throws InvalidException {

        // Get our to-do entry from the database
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(entryChanges.getId());

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();

            this.updateEntry(entryData, entryChanges);

            // Save the updated version to the database
            TodoEntry updatedTodo = this.todoRepository.save(entryData);

            // Return the updated full entry
            return updatedTodo;
        } else {
            // Throwing an invalid ID exception as the ID could not be found in the database
            throw new InvalidException();
        }
    }

    private void updateEntry(TodoEntry entryData, TodoEntryChanges changes) {
        // Update the entry
        if(changes.getTitle().isPresent()) {
            entryData.setTitle(changes.getTitle().get());
        }
        if(changes.getDescription().isPresent()) {
            entryData.setDescription(changes.getDescription().get());
        }
        if(changes.getCompleted().isPresent()) {
            entryData.setCompleted(changes.getCompleted().get());
        }
        if(changes.getCreatedAt().isPresent()) {
            entryData.setCreatedAt(changes.getCreatedAt().get());
        }
        if(changes.getDueOn().isPresent()) {
            entryData.setDueOn(changes.getDueOn().get());
        }
        if(changes.getCompletedOn().isPresent()) {
            entryData.setCompletedOn(changes.getCompletedOn().get());
        }
        if(changes.getLastModifiedAt().isPresent()) {
            entryData.setLastModifiedAt(changes.getLastModifiedAt().get());
        }
    }
    public void lastModifiedAt(TodoEntry entryData, TodoEntry changes){
        changes.setLastModifiedAt(Instant.now().toString());
        entryData.setLastModifiedAt(changes.getLastModifiedAt());
    }

    //delete service method

    public void deleteTodo ( long id) throws InvalidException {
        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

// Check if we found an entry
        if (originalEntry.isPresent()) {
            // Delete the entry we found by ID

            // Return a 204 OK No Content status, we deleted it
            this.todoRepository.deleteById(id);


        } else {
            // Return a 404 Not Found status, since we didn't find the entry
            throw new InvalidException();
        }

    }}
