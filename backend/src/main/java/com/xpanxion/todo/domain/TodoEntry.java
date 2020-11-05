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
    private String completedOn;


    @Column(length = 24)
    private String lastModifiedAt;


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

    public TodoEntry(Long id, String title, String description, String dateCreated,String dueOn, String completedOn, String lastModifiedAt, Boolean completed) {
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
    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn = completedOn;
    }

    public String getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(String lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


}