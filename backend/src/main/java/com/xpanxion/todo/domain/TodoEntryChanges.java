package com.xpanxion.todo.domain;


import java.util.Optional;

public class TodoEntryChanges {


    private final Long id;
    private Optional<String> title;
    private Optional<String> description;
    private Optional<String> createdAt;
    private Optional<Boolean> completed;
    private Optional<String> dueOn;
    private Optional<String> completedOn;
    private Optional<String> lastModifiedAt;

    public TodoEntryChanges(Long id) {
        this.id = id;
    }

    public TodoEntryChanges(Long id, Optional<String> title, Optional<String> description, Optional<String> dateCreated,  Optional<Boolean>  completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = dateCreated;
        this.completed = completed;
    }

    public TodoEntryChanges(Long id, Optional<String> title, Optional<String> description, Optional<String> dateCreated, Optional<Boolean> completed,Optional<String> dueOn, Optional<String> completedOn,Optional<String> last
         this.id = id;
         this.title = title;
         this.description = description;
         this.createdAt = dateCreated;
         this.dueOn = dueOn;
         this.completedOn = completedOn;
         this.lastModifiedAt = lastModifiedAt;
         this.completed = completed;
}

    public Long getId() {
        return id;
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

    public Optional<String> getDueOn() {
        return dueOn;
    }

    public void setDueOn(Optional<String> dueOn) {
        this.dueOn = dueOn;
    }


