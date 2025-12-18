package com.maksym.taskmanager.repo;

import com.maksym.taskmanager.model.Task;
import java.util.*;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<UUID, Task> storage = new HashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(UUID id) {
        storage.remove(id);
    }
}
