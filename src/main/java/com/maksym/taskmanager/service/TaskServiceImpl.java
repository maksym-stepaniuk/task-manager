package com.maksym.taskmanager.service;

import com.maksym.taskmanager.exception.ValidationException;
import com.maksym.taskmanager.exception.TaskNotFoundException;

import com.maksym.taskmanager.model.Task;
import com.maksym.taskmanager.model.Priority;

import com.maksym.taskmanager.repo.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskServiceImpl implements TaskService{
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task create(String title, String description, Priority priority, LocalDateTime dueAt) {
        if (title == null || title.isBlank()) {
            throw new ValidationException("title cannot be blank or null");
        }

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
}
