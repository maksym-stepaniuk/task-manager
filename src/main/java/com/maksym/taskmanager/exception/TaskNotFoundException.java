package com.maksym.taskmanager.exception;

import com.maksym.taskmanager.model.Task;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException (UUID id) {
        super("Task not found with id: " + id);
    }
}
