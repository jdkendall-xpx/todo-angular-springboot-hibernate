package com.xpanxion.todo.services;

import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xpanxion.todo.domain.TodoEntry;

import javax.swing.*;
import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    public void deleteTodo(long id) throws InvalidIdException {
        Optional<TodoEntry> originalEntry = this.todoRepository.findById(id);
        // check if we found an entry
        if (originalEntry.isPresent()) {
            //delete the entry we found by ID
            this.todoRepository.deleteById(id);
        } else {
            //throw an invalid ID exception since we didn't find the entry
        }
    }

    public TodoEntry createTodo(TodoEntry newEntry) {
        // Save our entry to database and return the saved entry
        TodoEntry savedEntry = this.todoRepository.save(newEntry);

        return savedEntry;

    }

    public TodoEntry updateTodo(long id, TodoEntry changes) throws InvalidIdException {
        // Get our to-do entry from the database
        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();

            this.updateEntry(entryData, changes);

            // Save the updated version to the database
            TodoEntry updatedTodo = todoRepository.save(entryData);

            // Return the updated full entry
            return updatedTodo;
        } else {
            // Throw invalid ID exception as ID couldn't be found in database

            throw new InvalidIdException();
        }


    }

    private void updateEntry(TodoEntry entryData, TodoEntry changes) {
        // Update the entry
        if (changes.getTitle() != null) {
            entryData.setTitle(changes.getDescription());
        }
        if (changes.getDescription() != null) {
            entryData.setDescription(changes.getDescription());
        }
        if (changes.getCompleted() != null) {
            entryData.setCompleted(changes.getCompleted());

            boolean isCompleted = changes.getCompleted();

            //If todo is marked complete,
            if (isCompleted == true) {
                // the database should be updated with a completed at date
                //entryData.setCompleteOn();
            }
            //if a  todo is marked incomplete,
            else {


                // the database should be updated with no completed at date
                //entryData.setCompletedOn();
            }
        }

            if (changes.getCreatedAt() != null){

                entryData.setCreatedAt(changes.getCreatedAt());
            }
        }
    }

