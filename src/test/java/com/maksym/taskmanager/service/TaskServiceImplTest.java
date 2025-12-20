package com.maksym.taskmanager.service;
import com.maksym.taskmanager.exception.TaskNotFoundException;
import com.maksym.taskmanager.exception.ValidationException;
import com.maksym.taskmanager.model.Priority;
import com.maksym.taskmanager.model.Task;
import com.maksym.taskmanager.repo.InMemoryTaskRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class TaskServiceImplTest {
    @Test
    void create_validData_CreatedTask() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        Task task = service.create(
            "Java",
            "Service layer",
            Priority.HIGH,
            null
        );

        assertNotNull(task.getId());
        assertEquals("Java", task.getTitle());
    }

    @Test
    void create_blankTitle_throwsException() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        assertThrows(
            ValidationException.class,
            () -> service.create(
                        " ",
                        "Service layer",
                        Priority.LOW,
                        null
            )
        );
    }

    @Test
    void findById_missing_returnsEmptyOptional() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        Optional<Task> result = service.findById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void delete_missingId_throwsException() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        assertThrows(
                TaskNotFoundException.class,
                () -> service.deleteById(UUID.randomUUID())
        );
    }
}
