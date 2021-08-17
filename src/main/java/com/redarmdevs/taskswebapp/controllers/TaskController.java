package com.redarmdevs.taskswebapp.controllers;

import com.redarmdevs.taskswebapp.exceptions.TaskException;
import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping(path = "api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping(path = "{user}")
    public Set<Task> getTasks(@PathVariable(name = "user") String user){
        try {
            return taskService.getTasks(user);
        } catch (UserException e) {
            return Collections.emptySet();
        }
    }

    @PostMapping(path = "{user}")
    public ResponseEntity<String> addTask(@PathVariable(name = "user") String username, @RequestBody Task task){
        String message;
        try {
            taskService.addTask(username, task);
            message = "task '" + task.getName() + "' added successfully!";
            return ResponseEntity.ok(message);
        }
        catch (TaskException e){
            message = "task failed to be added: " + e.getMessage();
            return ResponseEntity.badRequest().body(message);
        }
    }
}
