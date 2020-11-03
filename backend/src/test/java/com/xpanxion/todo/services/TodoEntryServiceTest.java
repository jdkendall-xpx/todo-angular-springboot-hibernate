package com.xpanxion.todo.services;

import com.xpanxion.todo.domain.TodoEntry;
import com.xpanxion.todo.domain.TodoEntryChanges;
import com.xpanxion.todo.exceptions.InvalidIdException;
import com.xpanxion.todo.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoEntryServiceTest {
    @InjectMocks
    TodoEntryService testee;

    @Mock
    TodoRepository mockTodoRepository;

    @Test
    void testUpdateTodo_HappyPath_NoChangesMade() throws InvalidIdException {
        // Given
        // the user calls with no changes to be made
        long id = 1;
        TodoEntryChanges changes = new TodoEntryChanges(
                id,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        TodoEntry dbEntry = new TodoEntry(
                id,
                "Title B",
                "Description B",
                "2020-10-21T13:33:41.000Z",
                true
        );

        Mockito.when(mockTodoRepository.findById(id))
                .thenReturn(Optional.of(dbEntry));

        Mockito.when(mockTodoRepository.save(dbEntry))
                .thenReturn(dbEntry);

        // When
        // we call updateTodo on an entry with that changeset
        TodoEntry result = this.testee.updateTodo(changes);

        // Then
        // nothing should have changed
        assertEquals(id, result.getId());
        assertEquals("Title B", result.getTitle());
        assertEquals("Description B", result.getDescription());
        assertEquals("2020-10-21T13:33:41.000Z", result.getCreatedAt());
        assertTrue(result.getCompleted());
    }

    @Test
    void testUpdateTodo_HappyPath_TitleChanged() throws InvalidIdException {
        // Given
        // the user calls with a changed title
        long id = 1;
        TodoEntryChanges changes = new TodoEntryChanges(
                id,
                Optional.of("New Title"),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        TodoEntry dbEntry = new TodoEntry(
                id,
                "Title B",
                "Description B",
                "2020-10-21T13:33:41.000Z",
                true
        );
        Mockito.when(mockTodoRepository.findById(id)).thenReturn(Optional.of(dbEntry));
        Mockito.when(mockTodoRepository.save(dbEntry)).thenReturn(dbEntry);

        // When
        // we call updateTodo on an entry with that changeset
        TodoEntry result = this.testee.updateTodo(changes);

        // Then
        // The title should have changed, but nothing else
        assertEquals(id, result.getId());
        assertEquals("New Title", result.getTitle());
        assertEquals("Description B", result.getDescription());
        assertEquals("2020-10-21T13:33:41.000Z", result.getCreatedAt());
        assertTrue(result.getCompleted());
    }

    @Test
    void testUpdateTodo_HappyPath_DescriptionChanged() throws InvalidIdException {
        // Given
        // the user calls with a changed title
        long id = 1;
        TodoEntryChanges changes = new TodoEntryChanges(
                id,
                Optional.empty(),
                Optional.of("New Description"),
                Optional.empty(),
                Optional.empty()
        );
        TodoEntry dbEntry = new TodoEntry(
                id,
                "Title B",
                "Description B",
                "2020-10-21T13:33:41.000Z",
                true
        );
        Mockito.when(mockTodoRepository.findById(id)).thenReturn(Optional.of(dbEntry));
        Mockito.when(mockTodoRepository.save(dbEntry)).thenReturn(dbEntry);

        // When
        // we call updateTodo on an entry with that changeset
        TodoEntry result = this.testee.updateTodo(changes);

        // Then
        // The title should have changed, but nothing else
        assertEquals(id, result.getId());
        assertEquals("Title B", result.getTitle());
        assertEquals("New Description", result.getDescription());
        assertEquals("2020-10-21T13:33:41.000Z", result.getCreatedAt());
        assertTrue(result.getCompleted());
    }

    @Test
    void testUpdateTodo_HappyPath_CompletedChanged() throws InvalidIdException {
        // Given
        // the user calls with a changed title
        long id = 1;
        TodoEntryChanges changes = new TodoEntryChanges(
                id,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(false)
        );
        TodoEntry dbEntry = new TodoEntry(
                id,
                "Title B",
                "Description B",
                "2020-10-21T13:33:41.000Z",
                true
        );
        Mockito.when(mockTodoRepository.findById(id)).thenReturn(Optional.of(dbEntry));
        Mockito.when(mockTodoRepository.save(dbEntry)).thenReturn(dbEntry);

        // When
        // we call updateTodo on an entry with that changeset
        TodoEntry result = this.testee.updateTodo(changes);

        // Then
        // The title should have changed, but nothing else
        assertEquals(id, result.getId());
        assertEquals("Title B", result.getTitle());
        assertEquals("Description B", result.getDescription());
        assertEquals("2020-10-21T13:33:41.000Z", result.getCreatedAt());
        assertFalse(result.getCompleted());
    }

    @Test
    void testUpdateTodo_HappyPath_CreatedAtChanged() throws InvalidIdException {
        // Given
        // the user calls with a changed title
        long id = 1;
        TodoEntryChanges changes = new TodoEntryChanges(
                id,
                Optional.empty(),
                Optional.empty(),
                Optional.of("2020-08-15T13:33:41.000Z"),
                Optional.empty()
        );
        TodoEntry dbEntry = new TodoEntry(
                id,
                "Title B",
                "Description B",
                "2020-10-21T13:33:41.000Z",
                true
        );
        Mockito.when(mockTodoRepository.findById(id)).thenReturn(Optional.of(dbEntry));
        Mockito.when(mockTodoRepository.save(dbEntry)).thenReturn(dbEntry);

        // When
        // we call updateTodo on an entry with that changeset
        TodoEntry result = this.testee.updateTodo(changes);

        // Then
        // The title should have changed, but nothing else
        assertEquals(id, result.getId());
        assertEquals("Title B", result.getTitle());
        assertEquals("Description B", result.getDescription());
        assertEquals("2020-08-15T13:33:41.000Z", result.getCreatedAt());
        assertTrue(result.getCompleted());
    }

    @Test
    void testUpdateTodo_InvalidId() {
        // Given
        long id = 100;
        TodoEntryChanges changes = new TodoEntryChanges(
                id,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );
        Mockito.when(mockTodoRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(InvalidIdException.class, () -> this.testee.updateTodo(changes));
    }
}