package com.maksym.taskmanager.repo;

import java.util.UUID;
import com.maksym.taskmanager.model.Task;

public interface TaskRepository extends Repository<UUID, Task> {
}
