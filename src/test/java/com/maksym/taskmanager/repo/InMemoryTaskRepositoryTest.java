package com.maksym.taskmanager.repo;

import com.maksym.taskmanager.model.Priority;
import com.maksym.taskmanager.model.Task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


public class InMemoryTaskRepositoryTest {
    private final InMemoryTaskRepository repository = new InMemoryTaskRepository();

    @Test
    void save_and_findById_ok() {
        Task task = new Task(
                UUID.randomUUID(),
                "Test",
                "Test",
                Priority.MEDIUM,
                null
        );

        repository.save(task);
        Optional<Task> result = repository.findById(task.getId());

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
    }

    @Test
    void deleteById_removesTask() {
        Task task = new Task(
                UUID.randomUUID(),
                "Test",
                "Delete",
                Priority.LOW,
                null
        );

        repository.save(task);
        repository.deleteById(task.getId());

        assertTrue(repository.findById(task.getId()).isEmpty());
    }

    @Test
    void findAll_returnsAllTasks() {
        repository.save(new Task(UUID.randomUUID(), "A", "Some text", Priority.HIGH, null));
        repository.save(new Task(UUID.randomUUID(), "B", "Some text", Priority.LOW, null));

        List<Task> tasks = repository.findAll();

        assertEquals(2, tasks.size());
    }
}
