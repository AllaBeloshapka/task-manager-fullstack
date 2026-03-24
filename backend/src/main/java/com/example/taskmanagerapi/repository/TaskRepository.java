package com.example.taskmanagerapi.repository;

import com.example.taskmanagerapi.entity.Task;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // All queries are scoped by owner to ensure data isolation per user
    List<Task> findByOwner(User owner);

    List<Task> findByOwnerAndStatus(User owner, TaskStatus status);

    List<Task> findByOwnerAndTitleContainingIgnoreCase(User owner, String keyword);

    List<Task> findByOwnerAndStatusAndTitleContainingIgnoreCase(
            User owner,
            TaskStatus status,
            String keyword
    );
}
