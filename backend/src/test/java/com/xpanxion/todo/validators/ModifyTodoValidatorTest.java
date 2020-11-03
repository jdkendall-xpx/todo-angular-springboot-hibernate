package com.xpanxion.todo.validators;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.ModifyTodoValidationException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModifyTodoValidatorTest {

    @Test
    void testValidate_HappyPath_NoErrors_EmptyChanges() throws ModifyTodoValidationException {
        // Given
        String idString = "125";
        TodoEntry todoChanges = new TodoEntry(
                null,
                null,
                null,
                null,
                null
        );

        // When
        ModifyTodoValidator testee = new ModifyTodoValidator();
        TodoEntryChanges result = testee.validate(idString, todoChanges);

        // Then
        // ID number was valid & parsed to a long
        assertEquals(125L, result.getId());

        // Null values should be converted to empty Optionals
        assertEquals(Optional.empty(), result.getTitle());
        assertEquals(Optional.empty(), result.getDescription());
        assertEquals(Optional.empty(), result.getCreatedAt());
        assertEquals(Optional.empty(), result.getCompleted());
    }

    @Test
    void testValidate_HappyPath_NoErrors_AllChangesRequested() throws ModifyTodoValidationException {
        // Given
        String idString = "64";
        TodoEntry todoChanges = new TodoEntry(
                null,
                "New Title",
                "New Desc",
                "2020-11-06T01:09:33.441Z",
                true
        );

        // When
        ModifyTodoValidator testee = new ModifyTodoValidator();
        TodoEntryChanges result = testee.validate(idString, todoChanges);

        // Then
        // ID number was valid & parsed to a long
        assertEquals(64L, result.getId());

        // Change values that are present should be converted to Optionals of those values
        assertEquals(Optional.of("New Title"), result.getTitle());
        assertEquals(Optional.of("New Desc"), result.getDescription());
        assertEquals(Optional.of("2020-11-06T01:09:33.441Z"), result.getCreatedAt());
        assertEquals(Optional.of(true), result.getCompleted());
    }

    @Test
    void testValidate_Exception_IDIsNotANumber() {
        // Given
        String idString = "dog";
        TodoEntry todoChanges = new TodoEntry(
                null,
                null,
                null,
                null,
                null
        );

        // When/Then
        ModifyTodoValidator testee = new ModifyTodoValidator();
        assertThrows(ModifyTodoValidationException.class, () -> testee.validate(idString, todoChanges));
    }

    // What happens if ID is null?
    @Test
    void testValidate_Exception_IDIsNull() {
        // Given
        String idString = null;
        TodoEntry todoChanges = new TodoEntry(
                null,
                null,
                null,
                null,
                null
        );

        // When/Then
        ModifyTodoValidator testee = new ModifyTodoValidator();
        assertThrows(ModifyTodoValidationException.class, () -> testee.validate(idString, todoChanges));
    }

    @Test
    void testValidate_Exception_TitleIsGreaterThanMaxColumnSize() {
        // Given
        String idString = "1";
        TodoEntry todoChanges = new TodoEntry(
                null,
                createSizedString(ModifyTodoValidator.MAX_TEXT_COLUMN_SIZE + 1),
                null,
                null,
                null
        );

        // When/Then
        ModifyTodoValidator testee = new ModifyTodoValidator();
        assertThrows(ModifyTodoValidationException.class, () -> testee.validate(idString, todoChanges));
    }

    // What happens if desc change is provided and is greater than max mediumtext column size?
    @Test
    void testValidate_Exception_DescriptionIsGreaterThanMaxColumnSize() {
        // Given
        String idString = "1";
        TodoEntry todoChanges = new TodoEntry(
                null,
                null,
                createSizedString(ModifyTodoValidator.MAX_MEDIUMTEXT_COLUMN_SIZE + 1),
                null,
                null
        );

        // When/Then
        ModifyTodoValidator testee = new ModifyTodoValidator();
        assertThrows(ModifyTodoValidationException.class, () -> testee.validate(idString, todoChanges));
    }

    @Test
    void testValidate_Exception_CreatedAtIsNotValidDate() {
        // Given
        String idString = "1";
        TodoEntry todoChanges = new TodoEntry(
                null,
                null,
                null,
                "not-a-valid-date",
                null
        );

        // When/Then
        ModifyTodoValidator testee = new ModifyTodoValidator();
        assertThrows(ModifyTodoValidationException.class, () -> testee.validate(idString, todoChanges));
    }

    private String createSizedString(int size) {
        return new String(new char[size]).replace('\0', ' ');
    }
}