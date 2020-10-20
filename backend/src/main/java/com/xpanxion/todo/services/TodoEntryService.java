package com.xpanxion.todo.services;
import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    public void getAllTodos() {
        // Decide how we want to sort our database results -- here we want to go by createdAt column in descending order
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

        // Return all found results
        List<TodoEntry> results = todoRepository.findAll(sortByCreatedAt);
        //return ResponseEntity.ok().body(results);
     return;
    }
    public void getTodoById(long id) throws InvalidIdException {
        // Get our to-do entry from the database
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();
            //Return  the to-do entry inside a 2
        } else {
                // throw invalid Id exception, since we didn't find the entry
                throw new InvalidIdException();
        }

    }


    public void deleteTodo(long id) throws InvalidIdException {
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Delete the entry we found by ID
            this.todoRepository.deleteById(id);

        } else {
            // throw invalid Id exception, since we didn't find the entry
            throw new InvalidIdException();
            //
        }
    }
    public TodoEntry createTodo(TodoEntry newEntry) {
        // Save our entry to the database and return the saved entry
        TodoEntry savedEntry = todoRepository.save(newEntry);
        return savedEntry;
    }
    public TodoEntry UpdateTodo(long id, TodoEntry Changes) throws InvalidIdException {
        // Get our to-do entry from the database
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();

            // Update the entry
            updateEntry(entryData, Changes);

            // Save the updated version to the database
            TodoEntry updatedTodo = this.todoRepository.save(entryData);

            // Return the updated full entry 

            return updatedTodo;

        } else {
            // throwing Invalid Id exception since the Id is not found in the database
            throw new InvalidIdException();

        }

    }

    private void updateEntry(TodoEntry entryData, TodoEntry changes) {
        //update entry
        if (changes.getTitle() != null) {

            entryData.setTitle(changes.getTitle());
        }
        if (changes.getDescription() != null) {
            entryData.setDescription(changes.getDescription());
        }
        if (changes.getCompleted() != null) {

            entryData.setCompleted(changes.getCompleted());
        }

        if (changes.getCreatedAt() != null) {
            entryData.setCreatedAt(changes.getCreatedAt());
        }

    }
}