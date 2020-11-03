package com.xpanxion.todo.services;

import com.xpanxion.todo.exceptions.InvalidDueOnException;
import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xpanxion.todo.domain.TodoEntry;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Date;
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
            throw new InvalidIdException();
        }
    }

    public TodoEntry createTodo(TodoEntry newEntry) {
        // Save our entry to database and return the saved entry
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

            // Save the updated version to the database
            TodoEntry updatedTodo = this.todoRepository.save(entryData);

            // Return the updated full entry
            return updatedTodo;
        } else {
            // Throw invalid ID exception as ID couldn't be found in database

            throw new InvalidIdException();
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
        if (changes.getCompleted() != null) {
            entryData.setCompleted(changes.getCompleted());

            if(changes.getCompleted() ==true){
            entryData.setCompletedOn(Instant.now().toString());
                //change modified date
                changeLastModifiedAtDate(entryData,changes);
            }
            else{
                entryData.setCompletedOn("Task not completed");
                //change modified date
                changeLastModifiedAtDate(entryData,changes);
            }


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

        if (changes.getCreatedAt() != null){

                entryData.setCreatedAt(changes.getCreatedAt());


               // System.out.printf("Did we change title?%s%n",
                        //(changedTitle ? "Yes : No"));
            }



        }
     private void changeLastModifiedAtDate(TodoEntry entryData, TodoEntry changes) {
      changes.setLastModifiedAt(Instant.now().toString());
         entryData.setLastModifiedAt(changes.getLastModifiedAt());
    }
    }

