package com.assignment.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a requested Task entity cannot be found,using its ID.
 *
 * It is annotated with @ResponseStatus(HttpStatus.NOT_FOUND) so that when this
 * exception is thrown by the controller, the response status code is automatically
 * set to 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Long id){
        super("Task not found with id: " + id);
    }
}
