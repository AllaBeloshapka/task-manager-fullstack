package com.example.taskmanagerapi.service;

import com.example.taskmanagerapi.dto.TaskRequest;
import com.example.taskmanagerapi.dto.TaskUpdateRequest;
import com.example.taskmanagerapi.entity.Task;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.enums.TaskStatus;
import com.example.taskmanagerapi.exception.AccessDeniedTaskException;
import com.example.taskmanagerapi.exception.TaskNotFoundException;
import com.example.taskmanagerapi.repository.TaskRepository;
import com.example.taskmanagerapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    // Repository for database operations
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Task createTask(TaskRequest request, User owner) {

        User managedOwner = userRepository.findById(owner.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.NEW); // default status
        task.setOwner(managedOwner);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(User user) {
        return taskRepository.findByOwner(user);
    }

    public Task getTaskById(Long id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        // Ownership check
        if (!task.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have access to this task");
        }

        return task;
    }

    public List<Task> searchTasks(String keyword, User user) {
        return taskRepository.findByOwnerAndTitleContainingIgnoreCase(user, keyword);
    }

    public List<Task> getUserTasksByStatus(User user, TaskStatus status) {
        return taskRepository.findByOwnerAndStatus(user, status);
    }

    @Transactional
    public Task updateTask(Long id, TaskUpdateRequest request, User user) {
        Task task = getTaskById(id, user); // Используем уже готовый метод с проверкой прав

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

    @Transactional
    public void deleteTask(Long id, User user) {
        Task task = getTaskById(id, user); // Снова используем проверку прав
        taskRepository.delete(task);
    }

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