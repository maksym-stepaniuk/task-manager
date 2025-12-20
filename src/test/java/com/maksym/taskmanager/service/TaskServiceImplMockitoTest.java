package com.maksym.taskmanager.service;

import com.maksym.taskmanager.exception.TaskNotFoundException;
import com.maksym.taskmanager.model.Priority;
import com.maksym.taskmanager.model.Task;
import com.maksym.taskmanager.repo.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplMockitoTest {
    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskServiceImpl service;

    @Test
    void create_callsRepositorySave() {
        Task task = service.create(
                "Task",
                "desc",
                Priority.HIGH,
                null
        );

        verify(repository).save(any(Task.class));
    }

    @Test
    void delete_callsRepositoryDeleteById() {
        UUID id = UUID.randomUUID();

        Task task = new Task(
                id,
                "Task",
                "desc",
                Priority.HIGH,
                null
        );

        when(repository.findById(id)).thenReturn(Optional.of(task));

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    void update_missingId_throwsException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> service.updateTitle(id, "New title")
        );

        verify(repository, never()).save(any());
    }
}
