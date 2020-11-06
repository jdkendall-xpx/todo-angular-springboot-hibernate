package com.xpanxion.todo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TodoEntry {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(length = 24)
    private String createdAt;

    @Column(length = 24)
    private String dueOn;

    @Column(length = 24)
    private String completedAt;

    private Boolean completed;

    @Column(length = 24)
    private String lastModifiedAt;

    public TodoEntry() {
    }

    public TodoEntry(Long id, String title, String description, String dateCreated, String dateDue, String completedOn, Boolean completed, String lastModified) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = dateCreated;
        this.dueOn = dateDue;
        this.completedAt = completedOn;
        this.completed = completed;
        this.lastModifiedAt = lastModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String dateCreated) {
        this.createdAt = dateCreated;
    }

    public String getDueOn() { return dueOn; }

    public void setDueOn(String dateDue) { this.dueOn = dateDue; }

    public String getCompletedOn() { return completedAt; }

    public void setCompletedOn(String completedOn) { this.completedAt = completedOn; }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getLastModifiedAt() { return lastModifiedAt; }

    public void setLastModifiedAt(String lastModified) { this.lastModifiedAt = lastModified; }
}