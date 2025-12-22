package com.maksym.taskmanager.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private final LocalDateTime createdAt;
    private LocalDateTime dueAt;

    public Task (
        UUID id,
        String title,
        String description,
        Priority priority,
        LocalDateTime dueAt
    ) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        this.id = Objects.requireNonNull(id);
        this.title = title;
        this.description = description == null ? "" : description;
        this.status = Status.TODO;
        this.priority = priority == null ? Priority.MEDIUM : priority;
        this.createdAt = LocalDateTime.now();
        this.dueAt = dueAt;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }
        this.title = title;
    }

    public String getDescription() {
        return  description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = Objects.requireNonNull(status);
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = Objects.requireNonNull(priority);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<LocalDateTime> getDueAt() {
        return Optional.ofNullable(dueAt);
    }

    public void setDueAt(LocalDateTime dueAt) {
        this.dueAt = dueAt;
    }
}
