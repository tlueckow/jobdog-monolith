package de.codekeepers.jobdog.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private String tags;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime publishAt = LocalDateTime.now();

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime publishedTimestamp;

    private int publishTrials = 0;

    public Job() {
    }

    public Job(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDateTime getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(LocalDateTime publishAt) {
        this.publishAt = publishAt;
    }

    public LocalDateTime getPublishedTimestamp() {
        return publishedTimestamp;
    }

    public void setPublishedTimestamp(LocalDateTime publishedTimestamp) {
        this.publishedTimestamp = publishedTimestamp;
    }

    public boolean isPublished() {
        return publishedTimestamp != null;
    }

    public int getPublishTrials() {
        return publishTrials;
    }

    public void setPublishTrials(int publishTrials) {
        this.publishTrials = publishTrials;
    }

    @Override
    public String toString() {
        return String.format("Job{id=%d, title=%s}", id, title);
    }

    public void incPublishTrials() {
        publishTrials ++;
    }
}
