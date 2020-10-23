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

<<<<<<< HEAD
    public enum UpdateResultType {
        SUCCESSFUL,
        INVALID_ID
=======
    public List getTodos() throws InvalidIdException {
        // Decide how we want to sort our database results -- here we want to go by createdAt column in descending order
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

        // Return all found results
        List<TodoEntry> results = todoRepository.findAll(sortByCreatedAt);
        return todoRepository.findAll(sortByCreatedAt);
    }

    public TodoEntry createTodo(TodoEntry newEntry) {
        //establishes all ISO values to avoid null usage
        newTodoValues(newEntry);

        // Save the updated version to the database
        TodoEntry updatedTodo = this.todoRepository.save(newEntry);

        return newEntry;
    }

    private void newTodoValues(TodoEntry newEntry) {
        Instant currentDate = Instant.now();
        String currentDateString = currentDate.toString();

        newEntry.setCreatedAt(currentDateString);
        newEntry.setDueOn(Instant.EPOCH.toString());
        newEntry.setLastModified(Instant.EPOCH.toString());
        newEntry.setCompletedOn(Instant.EPOCH.toString());
>>>>>>> c1b5e80... Updating createdAt, dueOn, lastModified, and completedOn for ToDo
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
        boolean hasBeenModified = false;

        //update the entry
        if(changes.getTitle() != null) {
            entryData.setTitle(changes.getTitle());
            hasBeenModified = true;
        }
        if(changes.getDescription() != null) {
            entryData.setDescription(changes.getDescription());
            hasBeenModified = true;
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

            entryData.setCompleted(isComplete);

            //If a todo is marked complete,
            if(isComplete == true) {
                // the database should be updated with a completed at date
                entryData.setCompletedOn(currentDateString);
            }

            //If a todo is marked incomplete,
            else {
                // the database should be updated with no completed at date
                entryData.setCompletedOn(null);
            }
        }

//        if(changes.getLastModified() != null) {
//            //entryData.setLastModified(changes.getLastModified());
//        }

        if(hasBeenModified) {
            Instant currentDate = Instant.now();
            String currentDateString = currentDate.toString();

            entryData.setLastModified(currentDateString);
        }



            //Instant currentDate = Instant.now();
            //String currentDateString = currentDate.toString();
            //entryData.setLastModified(currentDateString);

    }
}
