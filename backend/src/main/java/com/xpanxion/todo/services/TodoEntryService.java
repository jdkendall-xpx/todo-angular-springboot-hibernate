package main.java.com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.exceptions.InvalidExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
=======
import java.time.Instant;
import java.util.List;
>>>>>>> f51a29b... Updating completed and completedOn
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
        if(changes.getCreatedAt() != null) {
            //Instant currentDate = Instant.now();
            //String currentDateString = currentDate.toString();

            //entryData.setCreatedAt(currentDateString);
        }
        if(changes.getDueOn() != null) {
            entryData.setDueOn(changes.getDueOn());
            //Instant currentDate = Instant.now();
            //String currentDateString = currentDate.toString();

            //entryData.setDueOn(currentDateString);
        }
        if(changes.getCompleted() != null) {
            //entryData.setCompletedOn(changes.getCompletedOn());

            Instant currentDate = Instant.now();
            String currentDateString = currentDate.toString();

            Boolean isComplete = changes.getCompleted();

            //If a todo is marked complete,
            if(isComplete == true) {
                // the database should be updated with a completed at date
                entryData.setCompleted(true);
                entryData.setCompletedOn(currentDateString);
            }

            //If a todo is marked incomplete,
            else {
                // the database should be updated with no completed at date
                entryData.setCompletedOn(null);
            }
        }
        if(changes.getLastModified() != null) {

            //entryData.setLastModified(changes.getLastModified());

            //Instant currentDate = Instant.now();
            //String currentDateString = currentDate.toString();

            //entryData.setLastModified(currentDateString);
        }
    }
}
