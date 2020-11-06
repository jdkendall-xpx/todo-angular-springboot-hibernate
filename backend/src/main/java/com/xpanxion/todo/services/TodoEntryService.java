package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.InvalidDueOnException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
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
    public TodoEntry postTodo(TodoEntry todo) throws com.xpanxion.todo.exceptions.InvalidException {
        TodoEntry savedEntry = todoRepository.save(todo);
        return savedEntry;

    }
    //get by id service method
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
        //update service method

        public TodoEntry updateTodo ( TodoEntryChanges entryChanges) throws com.xpanxion.todo.exceptions.InvalidException {
            Optional<TodoEntry> originalEntry = this.todoRepository.findById(entryChanges.getId());

            // Check if we found an entry
            if (originalEntry.isPresent()) {
                // Use the entry's data
                TodoEntry entryData = originalEntry.get();

                // Update the entry
                this.updateEntry(entryData, entryChanges);
                // Save the updated version to the database
                TodoEntry updatedTodo = this.todoRepository.save(entryData);

                // Return the updated full entry
                return updatedTodo;
            } else {
                // throwing invalid id exception
                throw new com.xpanxion.todo.exceptions.InvalidException();
            }
        }

        private void updateEntry (TodoEntry entryData, TodoEntryChanges changes){
            if (changes.getTitle().isPresent()) {

                entryData.setTitle(changes.getTitle().get());
                //modify last modified at date after updates are made
                getLastModifiedDate(entryData);
            }

            if (changes.getDescription().isPresent()) {

                entryData.setDescription(changes.getDescription().get());
                //modify last modified at date after updates are made
                getLastModifiedDate(entryData);

            }

            if (changes.getCreatedAt().isPresent()) {

                entryData.setCreatedAt(changes.getCreatedAt().get());
                //modify last modified at date after updates are made
                getLastModifiedDate(entryData);


            }

            if (changes.getDueOn().isPresent()) {
                entryData.setDueOn(changes.getDueOn().get());

                //confirm that due on date isnt before created at date
                try {
                    Instant dueOnDate = Instant.parse(changes.getDueOn().get());
                    Instant createAtDate = Instant.parse(entryData.getCreatedAt());

                    if (dueOnDate.isAfter(createAtDate)) {
                        Instant currentDate = Instant.now();
                        String currentDateString = currentDate.toString();

                        entryData.setDueOn(currentDateString);
                        //modify last modified at date after updates are made
                        getLastModifiedDate(entryData);

                    }else{
                        throw new InvalidDueOnException("Due date was before created on date");
                    }

                }catch(DateTimeException ex){
                        throw new RuntimeException("Due date cannot be parsed");
                    }



            }


            if (changes.getCompleted().isPresent()) {

                entryData.setCompleted(changes.getCompleted().get());
                //modify date after entries are made

                //if a todo is marked complete, the completed on date should be have the date it was completed on
                if(changes.getCompleted().isPresent() == true && changes.getCompleted().get() ==true) {
                    //the database should be updated with a completed at date
                    entryData.setCompletedOn(Instant.now().toString());
                    //modify last modified at date after updates are made
                    getLastModifiedDate(entryData);
                }
                //if a todo is marked incomplete, the completed on date should be defined as incomplete
                else if(changes.getCompleted().isPresent() == true && changes.getCompleted().get() == false) {
//                    // the database should be updated with no completed at date
                    entryData.setCompletedOn("Task is not complete");
                    //modify last modified at date after updates are made
                    getLastModifiedDate(entryData);
                }



            }



        }

        //update last modified date after any change is made
   private void getLastModifiedDate(TodoEntry entryData){

           entryData.setLastModifiedAt(Instant.now().toString());




   }


    //delete service method

    public void deleteTodo ( long id) throws com.xpanxion.todo.exceptions.InvalidException {
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

                    }}

