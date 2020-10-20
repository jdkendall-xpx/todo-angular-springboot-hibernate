package main.java.com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.exceptions.InvalidExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    public enum UpdateResultType {
        SUCCESSFUL,
        INVALID_ID
    }

    interface UpdateResult {}
    class SuccessfulUpdateResult implements UpdateResult {

    }

    class InvalidResult implements UpdateResult {

    }

    public TodoEntry updateTodo(long id, TodoEntry changes) throws InvalidIdException {
        // Get our to-do entry from the database
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
            // Throwing an invalid ID exception as the ID could not e found in the database
            throw new InvalidIdException();
        }

    }

    private void updateEntry(TodoEntry entryData, TodoEntry changes) {
        //update the entry
        if(changes.getTitle() != null) {
            entryData.setTitle(changes.getTitle());
        }
        if(changes.getDescription() != null) {
            entryData.setDescription(changes.getDescription());
        }
        if(changes.getCompleted() != null) {
            entryData.setCompleted(changes.getCompleted());
        }
        if(changes.getCreatedAt() != null) {
            entryData.setCreatedAt(changes.getCreatedAt());
        }
        entryData.setTitle(changes.getTitle());
        entryData.setDescription(changes.getDescription());
        entryData.setCreatedAt(changes.getCreatedAt());
        entryData.setCompleted(changes.getCompleted());
    }
}
