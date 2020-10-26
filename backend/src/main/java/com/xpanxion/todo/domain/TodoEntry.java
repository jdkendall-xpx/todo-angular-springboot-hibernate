package com.xpanxion.todo.domain;

import javax.persistence.*;



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
    private String completedOn;
    private boolean completed;
    private String dueOn;
    private String lastModifiedAt;


    public TodoEntry() {
    }

    public TodoEntry(Long id, String title, String description, String dateCreated, String dateCompleted, Boolean completed, String dateDue, String dateLastModified) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = dateCreated;
        this.completedOn = dateCompleted;
        this.completed = completed;
        this.dueOn = dateDue;
        this.lastModifiedAt = dateLastModified;


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

    public String getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(String dateCompleted) {
        this.completedOn = dateCompleted;

    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dateDue) {
        this.dueOn = dateDue;
    }
public String getLastModifiedAt() {
        return lastModifiedAt;
}
public void setLastModifiedAt(String dateLastModified){
        this.lastModifiedAt= dateLastModified;
}
    public void setCompletedOn() {
    }


}