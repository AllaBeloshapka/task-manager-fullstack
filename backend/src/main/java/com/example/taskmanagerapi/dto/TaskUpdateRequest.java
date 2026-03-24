package com.example.taskmanagerapi.dto;

import com.example.taskmanagerapi.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskUpdateRequest {

    private String title;
    private String description;
    private TaskStatus status;
}