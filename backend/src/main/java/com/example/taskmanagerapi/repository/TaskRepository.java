package com.example.taskmanagerapi.repository;

import com.example.taskmanagerapi.entity.Task;
import com.example.taskmanagerapi.entity.User;
import com.example.taskmanagerapi.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // All queries are scoped by owner to ensure data isolation per user
    List<Task> findByOwner(User owner);

    //All tasks of a user with a specific status
    List<Task> findByOwnerAndStatus(User owner, TaskStatus status);

    //All tasks of a user with a specific title
    List<Task> findByOwnerAndTitleContainingIgnoreCase(User owner, String keyword);

    //All tasks of a user with a specific status and title
    List<Task> findByOwnerAndStatusAndTitleContainingIgnoreCase(
            User owner,
            TaskStatus status,
            String keyword
    );
}
