package main.java.com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
<<<<<<< HEAD
import com.xpanxion.todo.repositories.TodoRepository;
import com.xpanxion.todo.exceptions.InvalidExceptions;
=======
import com.xpanxion.todo.exceptions.InvalidDueOnException;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.InvalidPropertyException;
>>>>>>> faddadd... Updating DueOn error handling
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
import java.time.DateTimeException;
>>>>>>> faddadd... Updating DueOn error handling
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

    //sets todo values upon creation to avoid nulls
    private void newTodoValues(TodoEntry newEntry) {
        Instant currentDate = Instant.now();
        String currentDateString = currentDate.toString();

        newEntry.setCreatedAt(currentDateString);
        newEntry.setDueOn(Instant.EPOCH.toString());
        newEntry.setLastModified(Instant.EPOCH.toString());
        newEntry.setCompletedOn(Instant.EPOCH.toString());
>>>>>>> c1b5e80... Updating createdAt, dueOn, lastModified, and completedOn for ToDo
    }

<<<<<<< HEAD
    interface UpdateResult {}
    class SuccessfulUpdateResult implements UpdateResult {
=======
    public TodoEntry getTodo(long id) throws InvalidIdException {
        // Get our to-do entry from the database
        Optional<TodoEntry> originalEntry = todoRepository.findById(id);
>>>>>>> faddadd... Updating DueOn error handling

    }

<<<<<<< HEAD
    class InvalidResult implements UpdateResult {
=======
    public void deleteTodo(long id) throws InvalidIdException {
        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Delete the entry we found by ID
            todoRepository.deleteById(id);
            //no return required, functions automatically return when completed
        } else {
            //Throw an invalid ID exception since we didn't find the entry
            throw new InvalidIdException();

        }
>>>>>>> faddadd... Updating DueOn error handling

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

    private void updateEntry(TodoEntry entryData, TodoEntry changes) throws InvalidPropertyException {
        boolean hasBeenModified = false;

        //update the entry
        if (changes.getTitle() != null) {
            entryData.setTitle(changes.getTitle());
            hasBeenModified = true;
        }
        if (changes.getDescription() != null) {
            entryData.setDescription(changes.getDescription());
            hasBeenModified = true;
        }
        if (changes.getCreatedAt() != null) {
            //
            //check if this is necessary from edit perspective
            //
            //Instant currentDate = Instant.now();
            //String currentDateString = currentDate.toString();

            //entryData.setCreatedAt(currentDateString);
        }
        if (changes.getDueOn() != null) {
            try {
                Instant dueOnDate = Instant.parse(changes.getDueOn());
                Instant createdAtDate = Instant.parse(entryData.getCreatedAt());

                if (dueOnDate.isAfter(createdAtDate)) {

                    Instant currentDate = Instant.now();
                    String currentDateString = currentDate.toString();

                    entryData.setDueOn(currentDateString);
                } else {
                    throw new InvalidDueOnException("Due Date is in the past");
                }
            } catch (DateTimeException ex) {
                throw new InvalidDueOnException("Due Date could not be parsed");
            }
        }
        if (changes.getCompleted() != null) {
            //entryData.setCompletedOn(changes.getCompletedOn());

            Instant currentDate = Instant.now();
            String currentDateString = currentDate.toString();

            Boolean isComplete = changes.getCompleted();

            entryData.setCompleted(isComplete);

            //If a todo is marked complete,
            if (isComplete == true) {
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

        if (hasBeenModified) {
            Instant currentDate = Instant.now();
            String currentDateString = currentDate.toString();

            entryData.setLastModified(currentDateString);
        }


        //Instant currentDate = Instant.now();
        //String currentDateString = currentDate.toString();
        //entryData.setLastModified(currentDateString);

    }
}
