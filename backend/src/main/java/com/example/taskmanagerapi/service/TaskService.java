package com.example.taskmanagerapi.service;

import com.example.taskmanagerapi.dto.TaskRequest;
import com.example.taskmanagerapi.dto.TaskUpdateRequest;
import com.example.taskmanagerapi.entity.Task;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.enums.TaskStatus;
import com.example.taskmanagerapi.exception.AccessDeniedTaskException;
import com.example.taskmanagerapi.exception.TaskNotFoundException;
import com.example.taskmanagerapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for task-related operations.
 *
 * Responsibilities:
 * - Handles business logic for tasks
 * - Ensures ownership control (user can access only their tasks)
 * - Delegates persistence operations to the repository layer
 *
 * Note:
 * - This layer should contain all business rules
 * - Controllers must stay thin and only delegate here
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    // Repository for database operations
    private final TaskRepository taskRepository;

    /**
     * Creates a new task for a specific user.
     *
     * Flow:
     * - Maps request DTO to entity
     * - Sets default values (status, createdAt)
     * - Assigns owner
     * - Persists entity
     *
     * @param request task creation payload
     * @param owner authenticated user
     * @return created task entity
     */
    public Task createTask(TaskRequest request, User owner) {

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.NEW); // default status
        task.setOwner(owner);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    /**
     * Returns all tasks belonging to a specific user.
     *
     * @param user authenticated user
     * @return list of user tasks
     */
    public List<Task> getUserTasks(User user) {
        return taskRepository.findByOwner(user);
    }

    /**
     * Retrieves a task by id with ownership validation.
     *
     * @param id task id
     * @param user authenticated user
     * @return task if found and owned by user
     *
     * @throws TaskNotFoundException if task does not exist
     * @throws AccessDeniedTaskException if user is not the owner
     */
    public Task getTaskById(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        // Ownership check
        if (!task.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedTaskException("You do not have access to this task");
        }

        return task;
    }

    /**
     * Searches tasks by keyword for a specific user.
     *
     * @param keyword search keyword (case-insensitive)
     * @param user authenticated user
     * @return filtered list of tasks
     */
    public List<Task> searchTasks(String keyword, User user) {
        return taskRepository.findByOwnerAndTitleContainingIgnoreCase(user, keyword);
    }

    /**
     * Returns tasks filtered by status for a specific user.
     *
     * @param user authenticated user
     * @param status task status
     * @return filtered list of tasks
     */
    public List<Task> getUserTasksByStatus(User user, TaskStatus status) {
        return taskRepository.findByOwnerAndStatus(user, status);
    }

    /**
     * Updates an existing task with partial update logic.
     *
     * Flow:
     * - Fetch task
     * - Validate ownership
     * - Update only provided fields (null-safe)
     * - Persist updated entity
     *
     * @param id task id
     * @param request update payload (partial)
     * @param user authenticated user
     * @return updated task entity
     */
    public Task updateTask(Long id, TaskUpdateRequest request, User user) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        // Ownership check
        if (!task.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedTaskException("Access denied");
        }

        // Partial update (only non-null fields)
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        return taskRepository.save(task);
    }

    /**
     * Deletes a task with ownership validation.
     *
     * @param id task id
     * @param user authenticated user
     *
     * @throws TaskNotFoundException if task not found
     * @throws AccessDeniedTaskException if not owner
     */
    public void deleteTask(Long id, User user) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        // Ownership check
        if (!task.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedTaskException("Access denied");
        }

        taskRepository.delete(task);
    }

    /**
     * Flexible filtering of tasks by status and/or keyword.
     *
     * Logic:
     * - If both status and keyword provided → filter by both
     * - If only keyword → search by keyword
     * - If only status → filter by status
     * - If none → return all user tasks
     *
     * @param user authenticated user
     * @param status optional status filter
     * @param keyword optional keyword filter
     * @return filtered list of tasks
     */
    public List<Task> filterTasks(User user, TaskStatus status, String keyword) {

        boolean hasKeyword = keyword != null && !keyword.isBlank();

        if (hasKeyword && status != null) {
            return taskRepository.findByOwnerAndStatusAndTitleContainingIgnoreCase(user, status, keyword);
        }

        if (hasKeyword) {
            return taskRepository.findByOwnerAndTitleContainingIgnoreCase(user, keyword);
        }

        if (status != null) {
            return taskRepository.findByOwnerAndStatus(user, status);
        }

        return taskRepository.findByOwner(user);
    }
}