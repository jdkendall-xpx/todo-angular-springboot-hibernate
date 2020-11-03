package com.xpanxion.todo.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TodoEntryTest {
    @Test
    void testGetTitle_ReturnsGivenValue_ParameterizedConstructor_WithTitleA() {
        // Given
        TodoEntry entry = new TodoEntry(
        0L,
        "Title A",
        "Description A",
        "2020-08-19T13:33:41.000Z",
        false
        );

        // When
        String result = entry.getTitle();

        // Then
        assertEquals("Title A", result);
    }
    // Test
    @Test
    void testGetTitle_ReturnsGivenValue_ParameterizedConstructor_WithTitleB() {
        // Given
        TodoEntry entry = new TodoEntry(
        1L,
        "Title B",
        "Description B",
        "2020-10-21T13:33:41.000Z",
        true
        );

        // When
        String result = entry.getTitle();

        // Then
        assertEquals("Title B", result);
    }

    @Test
    void testGetTitle_ReturnsGivenValue_EmptyConstructor() {
        // Given
        TodoEntry entry = new TodoEntry();

        // When
        String result = entry.getTitle();

        // Then
        assertNull(result);
    }

    @Test
    void testSetTitle_ShouldSetValue() {
        // Given
        TodoEntry entry = new TodoEntry();

        // When
        entry.setTitle("Title A");

        // Then
        assertEquals("Title A", entry.getTitle());
    }
}
