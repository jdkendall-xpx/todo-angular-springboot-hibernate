package com.xpanxion.todo.domain;

import java.util.Optional;

public class TodoEntryChanges {
    private final long id;
    private Optional<String> title;
    private Optional<String> description;
    private Optional<String> createdAt;
    private Optional<Boolean> completed;

    public TodoEntryChanges(long id, Optional<String> title, Optional<String> description, Optional<String> dateCreated, Optional<Boolean> completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = dateCreated;
        this.completed = completed;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public void setTitle(Optional<String> title) {
        this.title = title;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public Optional<String> getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Optional<String> createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<Boolean> getCompleted() {
        return completed;
    }

    public void setCompleted(Optional<Boolean> completed) {
        this.completed = completed;
    }

    public long getId() {
        return id;
    }
}
