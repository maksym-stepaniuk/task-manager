package com.maksym.taskmanager.service;

import com.maksym.taskmanager.exception.ValidationException;
import com.maksym.taskmanager.exception.TaskNotFoundException;

import com.maksym.taskmanager.model.Status;
import com.maksym.taskmanager.model.Task;
import com.maksym.taskmanager.model.Priority;

import com.maksym.taskmanager.repo.TaskRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService{
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task create(String title, String description, Priority priority, LocalDateTime dueAt) {
        validateTitle(title);

        Task task = new Task(
                UUID.randomUUID(),
                title,
                description,
                priority,
                dueAt
        );

        return repository.save(task);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        if(repository.findById(id).isEmpty()) {
            throw new TaskNotFoundException(id);
        }

        repository.deleteById(id);
    }

    @Override
    public void updateTitle (UUID id, String newTitle) {
        validateTitle(newTitle);

        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(newTitle);
        repository.save(task);
    }

    @Override
    public List<Task> listByStatus(Status status) {
        return repository.findAll().stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    @Override
    public List<Task> search (String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }

        String normalizedQuery = query.trim().toLowerCase(Locale.ROOT);

        return repository.findAll().stream()
                .filter(task ->
                        task.getTitle().toLowerCase().contains(normalizedQuery)
                        || task.getDescription().toLowerCase().contains(normalizedQuery)
                )
                .toList();
    }

    @Override
    public List<Task> sortByPriorityThenDueAt() {
        return repository.findAll().stream()
                .sorted(
                        Comparator
                                .comparing(Task::getPriority)
                                .thenComparing(
                                        task -> task.getDueAt().orElse(null),
                                        Comparator.nullsLast(Comparator.naturalOrder())
                                )
                )
                .toList();
    }

    @Override
    public Map<Status, Long> countByStatus() {
        return repository.findAll().stream()
                .collect(
                        Collectors.groupingBy(
                                Task::getStatus,
                                Collectors.counting()
                        )
                );
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new ValidationException("title cannot be blank or null");
        }
    }
}
