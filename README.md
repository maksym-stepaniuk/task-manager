# In-memory Task Manager

Mini Java project for practicing core backend skills.

## Features
- Create, update and delete tasks
- Search and filter tasks using Streams
- Sort by priority and due date
- Count tasks by status
- Fully covered with unit tests

## Tech stack
- Java 21
- Maven
- JUnit 5
- Mockito

## Example usage

```java
TaskService service = new TaskServiceImpl(new InMemoryTaskRepository());

Task task = service.create(
    "Learn Java",
    "Streams and Mockito",
    Priority.HIGH,
    null
);

service.updateTitle(task.getId(), "Learn Java deeply");

List<Task> todos = service.listByStatus(Status.TODO);
