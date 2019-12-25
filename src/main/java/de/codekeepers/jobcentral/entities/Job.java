package de.codekeepers.jobcentral.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Future
    private LocalDateTime publishDateTime = LocalDateTime.now().plusMinutes(1);

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime publishedTimestamp;

    public Job() {
    }

    public Job(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public void setPublishedTimestamp(LocalDateTime publishedTimestamp) {
        this.publishedTimestamp = publishedTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public LocalDateTime getPublishedTimestamp() {
        return publishedTimestamp;
    }

    public boolean isPublished() {
        return publishedTimestamp != null;
    }

    @Override
    public String toString() {
        return "Job{" + "id=" + id + ", title=" + title + ", description=" + description + ", publishDateTime="
                + publishDateTime + '}';
    }
}
