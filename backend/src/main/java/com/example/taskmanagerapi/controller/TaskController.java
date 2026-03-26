package com.example.taskmanagerapi.controller;

import com.example.taskmanagerapi.dto.TaskRequest;
import com.example.taskmanagerapi.dto.TaskResponse;
import com.example.taskmanagerapi.dto.TaskUpdateRequest;
import com.example.taskmanagerapi.entity.Task;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.enums.TaskStatus;
import com.example.taskmanagerapi.repository.UserRepository;
import com.example.taskmanagerapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for task-related endpoints.
 * All endpoints require authentication.
 */

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    /**
     * Create a new task.
     */
    @PostMapping
    public TaskResponse createTask(
            @RequestBody TaskRequest request
    ) {

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No user found"));

        Task task = taskService.createTask(request, user);

        return mapToResponse(task);
    }

    /**
     * Update task.
     */
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request
    ) {

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No user found"));

        Task updatedTask = taskService.updateTask(id, request, user);

        return mapToResponse(updatedTask);
    }

    /**
     * Delete task.
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No user found"));

        taskService.deleteTask(id, user);
    }

    /**
     * Get all tasks.
     */
    @GetMapping
    public List<TaskResponse> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String keyword
    ) {

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No user found"));

        List<Task> tasks = taskService.filterTasks(user, status, keyword);

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Convert Task to DTO.
     */
    private TaskResponse mapToResponse(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setCreatedAt(task.getCreatedAt());
        return response;
    }
}
