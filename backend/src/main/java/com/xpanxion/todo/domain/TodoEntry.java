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
    private String lastModified;

    @Column(length = 24)
    private String completedOn;

    private Boolean completed;

    public TodoEntry() {
    }

    public TodoEntry(Long id, String title, String description, String dateCreated,String dueOn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = dateCreated;
        this.dueOn = dueOn;

    }

    public TodoEntry(Long id, String title, String description, String dateCreated,
                     Boolean completed, String dueOn, String lastModified, String completedOn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = dateCreated;
        this.completed = completed;
        this.dueOn = dueOn;
        this.lastModified = lastModified;
        this.completedOn = completedOn;

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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDueOn() { return dueOn; }

    public void setDueOn(String dueOn) { this.dueOn = dueOn; }

    public String getLastModified() { return lastModified; }

    public void setLastModified(String lastModified) { this.lastModified = lastModified; }

    public String getCompletedOn() { return completedOn; }

    public void setCompletedOn(String completedOn) { this.completedOn = completedOn; }
}