package com.xpanxion.todo.validators;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.ModifyTodoValidationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class ModifyTodoValidation {

    public static final int MAX_TEXT_COLUMN_SIZE = 65535;
    public static final int MAX_MEDIUMTEXT_COLUMN_SIZE = 16777215;

    public TodoEntryChanges validate(String idString, TodoEntry todoChanges) throws ModifyTodoValidationException {
        long id = this.validateAndParseID(idString);
        this.validateTitleLength(todoChanges);
        this.validateDescriptionLength(todoChanges);
        this.validateCreatedAtIsISOFormat(todoChanges);
        this.validateDueOnIsISOFormat(todoChanges);
        this.validateCompletedOnIsISOFormat(todoChanges);
        this.validateLastModifiedAtIsISOFormat(todoChanges);

        Optional<String> maybeTitle = Optional.ofNullable(todoChanges.getTitle());
        Optional<String> maybeDesc  = Optional.ofNullable(todoChanges.getDescription());
        Optional<String> maybeDateCreated = Optional.ofNullable(todoChanges.getCreatedAt());
        Optional<Boolean> maybeCompleted = Optional.ofNullable(todoChanges.getCompleted());
        Optional<String> maybeDueOn = Optional.ofNullable(todoChanges.getDueOn());
        Optional<String> maybeCompletedOn  = Optional.ofNullable(todoChanges.getCompletedOn());
        Optional<String> maybeLastModifiedAt = Optional.ofNullable(todoChanges.getLastModifiedAt());

        return new TodoEntryChanges(id, maybeTitle, maybeDesc, maybeDateCreated, maybeCompleted,maybeDueOn,maybeCompletedOn,maybeLastModifiedAt);
    }

    private long validateAndParseID(String idString) throws ModifyTodoValidationException {
        try {
            // Turn our string into an ID number
            return Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            throw new ModifyTodoValidationException("Invalid ID was passed. ID must be a number.");
        }
    }

    private void validateTitleLength(TodoEntry todoChanges) throws ModifyTodoValidationException {
        if(todoChanges.getTitle() != null && todoChanges.getTitle().length() > MAX_TEXT_COLUMN_SIZE) {
            throw new ModifyTodoValidationException("Invalid title was passed. Title must be 65,535 characters or less in length.");
        }
    }

    private void validateDescriptionLength(TodoEntry todoChanges) throws ModifyTodoValidationException {
        if(todoChanges.getDescription() != null && todoChanges.getDescription().length() > MAX_MEDIUMTEXT_COLUMN_SIZE) {
            throw new ModifyTodoValidationException("Invalid description was passed. Description must be 16,777,215 characters or less in length.");
        }
    }

    private void validateCreatedAtIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidationException {
        if(todoChanges.getCreatedAt() != null) {
            try {
                Instant.parse(todoChanges.getCreatedAt());
            } catch(DateTimeParseException ex) {
                throw new ModifyTodoValidationException("Invalid created at date was passed. Dates must be in ISO format YYYY-MM-DD`T`HH:mm:ss.SSSZ");
            }
        }
    }

    private void validateDueOnIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidationException {
        if(todoChanges.getDueOn() != null) {
            try {
                Instant.parse(todoChanges.getDueOn());
            } catch(DateTimeParseException ex) {
                throw new ModifyTodoValidationException("Invalid created at date was passed. Dates must be in ISO format YYYY-MM-DD`T`HH:mm:ss.SSSZ");
            }
        }
    }
    private void validateCompletedOnIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidationException {
        if(todoChanges.getCompleted() != null) {
            try {
                Instant.parse(todoChanges.getCompletedOn());
            } catch(DateTimeParseException ex) {
                throw new ModifyTodoValidationException("Invalid created at date was passed. Dates must be in ISO format YYYY-MM-DD`T`HH:mm:ss.SSSZ");
            }
        }
    }
    private void validateLastModifiedAtIsISOFormat(TodoEntry todoChanges) throws ModifyTodoValidationException {
        if(todoChanges.getLastModifiedAt() != null) {
            try {
                Instant.parse(todoChanges.getLastModifiedAt());
            } catch(DateTimeParseException ex) {
                throw new ModifyTodoValidationException("Invalid created at date was passed. Dates must be in ISO format YYYY-MM-DD`T`HH:mm:ss.SSSZ");
            }
        }
    }
}