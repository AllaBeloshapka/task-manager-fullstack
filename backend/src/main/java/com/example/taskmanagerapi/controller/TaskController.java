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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    /**
     * Создание новой задачи.
     * Теперь метод помечен @PostMapping и принимает только Body.
     */
    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request, Principal principal) {
        User user = getCurrentUser(principal); // Получаем текущего пользователя
        Task task = taskService.createTask(request, user);
        return mapToResponse(task);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @RequestBody TaskUpdateRequest request,
            Principal principal
    ) {
        User user = getCurrentUser(principal);
        Task updatedTask = taskService.updateTask(id, request, user);
        return mapToResponse(updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, Principal principal) {
        User user = getCurrentUser(principal);
        taskService.deleteTask(id, user);
    }

    @GetMapping
    public List<TaskResponse> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) String keyword,
            Principal principal
    ) {
        User user = getCurrentUser(principal);
        List<Task> tasks = taskService.filterTasks(user, status, keyword);

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Временный метод для получения пользователя.
     * В будущем здесь будет: SecurityContextHolder.getContext().getAuthentication()...
     */
    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }
        String username = principal.getName(); // Получаем username из Basic Auth
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

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
