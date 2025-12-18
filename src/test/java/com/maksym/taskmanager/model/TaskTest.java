package com.maksym.taskmanager.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



import java.time.LocalDateTime;
import java.util.UUID;

public class TaskTest {
    @Test
    void createTask_ok() {
        Task task = new Task(
                UUID.randomUUID(),
                "Java learning",
                "Learn generics and stream",
                Priority.HIGH,
                LocalDateTime.now()
        );

        assertEquals(Status.TODO, task.getStatus());
        assertEquals("Java learning", task.getTitle());
    }

    @Test
    void createTask_blankTitle_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Task(
                        UUID.randomUUID(),
                        " ",
                        "Learn",
                        Priority.LOW,
                        LocalDateTime.now()
                )
        );
    }
}
