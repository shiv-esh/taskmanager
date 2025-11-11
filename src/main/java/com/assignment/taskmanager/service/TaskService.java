package com.assignment.taskmanager.service;

import com.assignment.taskmanager.dto.TaskCreateRequest;

import com.assignment.taskmanager.dto.TaskResponse;
import com.assignment.taskmanager.dto.TaskUpdateRequest;
import com.assignment.taskmanager.exceptions.TaskNotFoundException;
import com.assignment.taskmanager.model.Task;
import com.assignment.taskmanager.model.TaskStatus;
import com.assignment.taskmanager.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        Task task = Task.builder()
                .title(taskCreateRequest.getTitle())
                .description(taskCreateRequest.getDescription())
                .status(taskCreateRequest.getStatus())
                .build();

         Task savedTask = repository.save(task);
         return mapToResponse(savedTask);
    }

    public TaskResponse getTaskById(Long id) {
        Task task =  repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return mapToResponse(task);
    }

    public List<TaskResponse> getAllTasks(int page, int size) {
        if(page < 0) page = 0;
        if(size <= 0 || size > 100) size = 10;
        return repository.findAll(PageRequest.of(page,size))
                .getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<TaskResponse> getTaskByStatus(TaskStatus status) {
        return repository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TaskResponse updateTask(Long id, TaskUpdateRequest taskUpdateRequest) {
        Task existingTask = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        //Validate if title is empty
        if(taskUpdateRequest.getTitle() != null){
            String trimmed = taskUpdateRequest.getTitle().trim();
            if (trimmed.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Title cannot be empty"
                );
            }
            existingTask.setTitle(trimmed);
        }
        if (taskUpdateRequest.getDescription() != null) {
            existingTask.setDescription(taskUpdateRequest.getDescription().trim());
        }
        if (taskUpdateRequest.getStatus() != null) {
            existingTask.setStatus(taskUpdateRequest.getStatus());
        }
        Task updatedTask = repository.save(existingTask);
        return mapToResponse(updatedTask);
    }


    public void deleteTask(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        repository.delete(task);
    }

    /**
     * Converts a Task entity into a TaskResponse DTO.
     *
     * @param task the Task entity to map
     * @return TaskResponse representing the entity
     */
    private TaskResponse mapToResponse(Task task){
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setCreatedAt(task.getCreatedAt());
        taskResponse.setUpdatedAt(task.getUpdatedAt());

        return taskResponse;

    }
}
