package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.exceptions.InvalidException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        }
        if (changes.getDescription() != null) {

            entryData.setDescription(changes.getDescription());


        }
        if (changes.getCreatedAt() != null) {

            entryData.setCreatedAt(changes.getCreatedAt());


        }

        if (changes.getDueOn() != null) {

            entryData.setDueOn(changes.getDueOn());


        }
        if (changes.getCompletedOn() != null) {

            entryData.setCompletedOn(changes.getCompletedOn());
            //completed on date if todo is completed
//            if(changes.getCompletedOn == true){
//
//            }
//            else(changes.getCompletedOn ==false){
//
//            }

        }
        if (changes.getLastModifiedAt() != null) {

            entryData.setLastModifiedAt(changes.getLastModifiedAt());


        }
        if (changes.getCompleted() != null) {

            entryData.setCompleted(changes.getCompleted());


        }
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
