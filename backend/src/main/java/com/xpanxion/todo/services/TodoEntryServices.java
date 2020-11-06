package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidException;
import com.xpanxion.todo.repositories.TodoRepository;
import jdk.nashorn.internal.objects.NativeDate;
import jdk.nashorn.internal.objects.NativeMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.Instant;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoEntryServices {
    @Autowired
    TodoRepository todoRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    Date date = new Date();

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

    private void updateEntry(TodoEntry entryData, TodoEntry changes) {
        boolean changedTitle = false;
        // Update the entry
        if (changes.getTitle() != null) {
              entryData.setTitle(changes.getTitle());
              changedTitle = true;
              //change modified date
              changeLastModifiedAtDate(entryData,changes);

        }
        if (changes.getDescription() != null) {
              entryData.setDescription(changes.getDescription());
              //change modified date
            changeLastModifiedAtDate(entryData,changes);


        }
        if (changes.getDueOn() != null) {
            try {
                Instant dueOnDate = Instant.parse(changes.getDueOn());
                Instant createAtDate = Instant.parse(entryData.getCreatedAt());

                if (dueOnDate.isAfter(createAtDate)) {
                    Instant currentDate = Instant.now();
                    String currentDateString = currentDate.toString();

                    entryData.setDueOn(currentDateString);
                    //modify date after entries are made
                    //change modified date
                    changeLastModifiedAtDate(entryData,changes);
                }else{
                    throw new InvalidDueOnException("Due date was before created on date");
                }

            }catch(DateTimeException ex){
                throw new RuntimeException("Due date cannot be parsed");
            }


        }
        if (changes.getCreatedAt() != null) {
              entryData.setCreatedAt(changes.getCreatedAt());


        }
        if (changes.getCompletedOn() != null) {
              entryData.setCompletedOn(changes.getCompletedOn());
              //change modified date
            changLastModifiedAtDate(entryData,changes);


        }
        if (changes.getCompleted() != null) {
              entryData.setCompleted(changes.getCompleted());
            changLastModifiedAtDate(entryData,changes);
        System.out.println("Did we change title? " + (changedTitle ? "Yes" : "No"));
            // If a todo is marked complete,
            if (changes.getCompleted() == true) {
                // the database should be updated with a completed at date
                entryData.getCompleted();
            }
            // If a todo is marked incomplete,
            else {
                // the database should be updated with no completed at date
                entryData.setCompletedOn("Not complete");
            }

        }
    }




    public void deleteTodo(long id) throws com.xpanxion.todo.exceptions.InvalidException {
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

    }
}

