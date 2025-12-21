package com.maksym.taskmanager.service;
import com.maksym.taskmanager.exception.TaskNotFoundException;
import com.maksym.taskmanager.exception.ValidationException;
import com.maksym.taskmanager.model.Priority;
import com.maksym.taskmanager.model.Status;
import com.maksym.taskmanager.model.Task;
import com.maksym.taskmanager.repo.InMemoryTaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
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

    @Test
    void updateTitle_existingTask_updatesTitle() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        Task task = service.create("Title", "desc", Priority.HIGH, null);

        service.updateTitle(task.getId(), "Updated title");

        Task updated = service.findById(task.getId())
                .orElseThrow();

        assertEquals("Updated title", updated.getTitle());
    }

    @Test
    void updateTitle_missingTask_throwsException() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        assertThrows(
                TaskNotFoundException.class,
                () -> service.updateTitle(UUID.randomUUID(), "New title")
        );
    }

    @Test
    void updateTitle_blankTitle_throwsValidationException() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        Task task = service.create("Title", "desc", Priority.HIGH, null);

        assertThrows(
                ValidationException.class,
                () -> service.updateTitle(task.getId(), " ")
        );
    }

    @Test
    void listByStatus_shouldReturnOnlyTasksWithGivenStatus() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        Task t1 = service.create("Task 1", "desc", Priority.HIGH, null);
        Task t2 = service.create("Task 2", "desc", Priority.LOW, null);

        t2.setStatus(Status.DONE);

        List<Task> result = service.listByStatus(Status.TODO);

        assertEquals(1, result.size());
        assertEquals(t1.getId(), result.get(0).getId());
    }

    @ParameterizedTest
    @ValueSource(strings = { "JAVA", "java", " stream " })
    void search_shouldReturnTasksByTitleOrDescription(String query) {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        service.create("Java learning", "Streams and generics", Priority.HIGH, null);

        List<Task> result = service.search(query);

        assertEquals(1, result.size());
    }

    @Test
    void sortByPriorityThenDueAt_returnsSortedTasks() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

        Task t1 = service.create("Task 1", "desc", Priority.HIGH, LocalDateTime.of(2025, 1, 10, 10, 0));
        Task t2 = service.create("Task 2", "desc", Priority.LOW, LocalDateTime.of(2025, 1, 10, 10, 0));
        Task t3 = service.create("Task 3", "desc", Priority.HIGH, LocalDateTime.of(2025, 1, 5, 11, 25));

        List<Task> result = service.sortByPriorityThenDueAt();

        assertEquals(List.of(t2, t3, t1), result);
    }
    
    @Test
    void countByStatus_returnsCorrectCounts() {
        TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());
        
        Task t1 = service.create("Task 1", "desc", Priority.HIGH, null);
        Task t2 = service.create("Task 2", "desc", Priority.HIGH, null);
        Task t3 = service.create("Task 3", "desc", Priority.HIGH, null);
        
        t1.setStatus(Status.IN_PROGRESS);
        t3.setStatus(Status.IN_PROGRESS);
        
        Map<Status, Long> result = service.countByStatus();
        
        assertEquals(1L, result.get(Status.TODO));
        assertEquals(2L, result.get(Status.IN_PROGRESS));
    }
}
