package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    public void deleteTodo(long id) throws InvalidIdException {
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Delete the entry we found by ID
            this.todoRepository.deleteById(id);
        } else {
            // Throw an invalid ID exception since we didn't find the entry since we didn't find the entry
            throw new InvalidIdException();
        }


    }

    public  TodoEntry createTodo(TodoEntry newEntry){
        // Save our entry to the database and return the saved entry
        TodoEntry savedEntry = this.todoRepository.save(newEntry);

        return savedEntry;
    }

    public TodoEntry updateTodo(long id, TodoEntry changes) throws InvalidIdException {

        // Get our to-do entry from the database
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();

            this.updateEntry(entryData, changes);

            //Save the updated version to the database
            TodoEntry updatedTodo = this.todoRepository.save(entryData);

            //Return the updated full entry
            return updatedTodo;
        } else {
            //Throwing an invalid ID exception as the ID could not be found in the database
            throw new InvalidIdException();
        }
    }

    private void updateEntry(TodoEntry entryData, TodoEntry changes) {
        // Update the entry
        if (changes.getTitle() != null) {
            entryData.setTitle(changes.getTitle());

        }
        if (changes.getDescription() != null) {
            entryData.setDescription((changes.getDescription()));

        }
        if (changes.getCompleted()) {
            entryData.setCompleted(changes.getCompleted());

        }
        if (changes.getCreatedAt() != null) {
            entryData.setCreatedAt(changes.getCreatedAt());
        }
    }
}

