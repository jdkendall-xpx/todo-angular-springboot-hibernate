package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TodoEntryService {
    @Autowired
    TodoRepository todoRepository;

    //get service method
    public List getTodos() {
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");

        // Return all found results
        return todoRepository.findAll(sortByCreatedAt);

    }
    //post by id service method
    public TodoEntry postTodo(TodoEntry todo) throws InvalidException {
        TodoEntry savedEntry = todoRepository.save(todo);
        return savedEntry;

    }
    //get by id service method
    public TodoEntry getTodoById(long id) throws InvalidException {


        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

        // Check if we found an entry
        if (originalEntry.isPresent()) {
            // Use the entry's data
            TodoEntry entryData = originalEntry.get();

            // Return the to-do entry inside a 200 OK response
            return entryData;
        } else {
            // Return a 404 Not Found status, since we didn't find the entry
            throw new InvalidException();
        }

    }
    //update service method

    public TodoEntry updateTodo ( long id, TodoEntry changes) throws InvalidException {
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
            throw new InvalidException();
        }
    }

    private void updateEntry (TodoEntry entryData, TodoEntry changes){
        if (changes.getTitle() != null) {

            entryData.setTitle(changes.getTitle());

            //update when last modified
            lastModifiedAt(entryData,changes);
        }
        if (changes.getDescription() != null) {

            entryData.setDescription(changes.getDescription());


            //update when last modified
            lastModifiedAt(entryData,changes);

        }
        //get created at
        if (changes.getCreatedAt() != null) {

            entryData.setCreatedAt(changes.getCreatedAt());



        }



        if (changes.getCompleted() != null) {

            entryData.setCompleted(changes.getCompleted());
            //completed on date if todo is completed
            if(changes.getCompleted() == true){
               entryData.setCompletedOn(Instant.now().toString());


                //update when last modified
                lastModifiedAt(entryData,changes);

            }
            else{
                entryData.setCompletedOn("Task not complete");


                //update when last modified
                lastModifiedAt(entryData,changes);

            }

        }
    }
    public void lastModifiedAt(TodoEntry entryData, TodoEntry changes){
        changes.setLastModifiedAt(Instant.now().toString());
        entryData.setLastModifiedAt(changes.getLastModifiedAt());
    }

    //delete service method

    public void deleteTodo ( long id) throws InvalidException {
        Optional<TodoEntry> originalEntry = todoRepository.findById(id);

// Check if we found an entry
        if (originalEntry.isPresent()) {
            // Delete the entry we found by ID

            // Return a 204 OK No Content status, we deleted it
            this.todoRepository.deleteById(id);


        } else {
            // Return a 404 Not Found status, since we didn't find the entry
            throw new InvalidException();
        }

    }}
