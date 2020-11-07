package com.xpanxion.todo.validators;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;

import com.xpanxion.todo.exceptions.ModifyTodoValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ModifyTodoValidator {

    public TodoEntryChanges validate(String idString, TodoEntry todoChanges) throws ModifyTodoValidationException {
        long id;
        try {
            // Turn our string into an ID number
            id = Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            // Return a 400 Bad Request response, we did not pass a number as an ID
            throw new ModifyTodoValidationException("Invalid ID was passed. ID must be a number");
        }
    }
}
