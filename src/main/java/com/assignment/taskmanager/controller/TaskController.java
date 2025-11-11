package com.assignment.taskmanager.controller;

import com.assignment.taskmanager.dto.TaskCreateRequest;
import com.assignment.taskmanager.dto.TaskCreateRequest;
import com.assignment.taskmanager.dto.TaskResponse;
import com.assignment.taskmanager.dto.TaskUpdateRequest;
import com.assignment.taskmanager.model.TaskStatus;
import com.assignment.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest taskCreateRequest){
        return ResponseEntity.ok(service.createTask(taskCreateRequest));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTaskById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,

                                                          @RequestParam(required = false) TaskStatus status) {

        // If status is provided, fetch filtered tasks
        if(status != null){
            return ResponseEntity.ok(service.getTaskByStatus(status));
        }
        // Otherwise, fetch all tasks (paginated)
        return ResponseEntity.ok(service.getAllTasks(page,size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateRequest taskUpdateRequest) {
        return ResponseEntity.ok(service.updateTask(id,taskUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}
