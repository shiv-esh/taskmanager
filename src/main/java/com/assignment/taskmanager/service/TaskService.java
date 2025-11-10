package com.assignment.taskmanager.service;

import com.assignment.taskmanager.dto.TaskRequest;

import com.assignment.taskmanager.dto.TaskResponse;
import com.assignment.taskmanager.exceptions.TaskNotFoundException;
import com.assignment.taskmanager.model.Task;
import com.assignment.taskmanager.model.TaskStatus;
import com.assignment.taskmanager.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .status(taskRequest.getStatus())
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

    public TaskResponse updateTask(Long id, @Valid TaskRequest taskRequest) {
        Task existingTask = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        existingTask.setTitle(taskRequest.getTitle());
        existingTask.setDescription(taskRequest.getDescription());
        existingTask.setStatus(taskRequest.getStatus());

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
