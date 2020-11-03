package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    public TodoEntry updateTodo(TodoEntryChanges entryChanges) throws InvalidIdException {

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
            throw new InvalidIdException();
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
    }
}
