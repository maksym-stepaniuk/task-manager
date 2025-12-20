package com.maksym.taskmanager.service;

import java.time.LocalDateTime;
import java.util.*;
import com.maksym.taskmanager.model.*;

public interface TaskService {
    Task create(
            String title,
            String description,
            Priority priority,
            LocalDateTime dueAt
    );

    Optional<Task> findById (UUID id);

    List<Task> findAll();

    void deleteById(UUID id);
}
