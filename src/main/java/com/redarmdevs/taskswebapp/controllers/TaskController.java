package com.redarmdevs.taskswebapp.controllers;

import com.redarmdevs.taskswebapp.exceptions.TaskException;
import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.payload.response.MessageResponse;
import com.redarmdevs.taskswebapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping(path = "{user}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTasks(@PathVariable(name = "user") String user, Principal principal){
        if(!principal.getName().equals(user))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unauthorized"));
        try {
            return ResponseEntity.ok(taskService.getTasks(user));
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "{user}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addTask(@PathVariable(name = "user") String username,
                                          @RequestBody Task task,
                                          Principal principal){
        if(!principal.getName().equals(username))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unauthorized"));
        try {
            taskService.addTask(username, task);
            return ResponseEntity.ok(new MessageResponse("task '" + task.getName() + "' added successfully!"));
        }
        catch (UserException e){
            return ResponseEntity.badRequest().body(new MessageResponse("task failed to be added: " + e.getMessage()));
        }
    }

    @DeleteMapping(path = {"{user}"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteTask(@PathVariable(name="user") String username,
                                             @RequestParam(name = "taskID") Long taskID,
                                             Principal principal){
        if(!principal.getName().equals(username))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unauthorized"));
        try{
            taskService.deleteTask(username, taskID);
            return ResponseEntity.ok(new MessageResponse("task deleted successfully"));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "{user}/deleteTasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteAllUsersTasks(@PathVariable(name="user") String username,
                                                      Principal principal){
        if(!principal.getName().equals(username))
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unauthorized"));
        try{
            taskService.deleteAllTasksByUser(username);
            return ResponseEntity.ok(new MessageResponse("All tasks by " + username + " deleted successfully"));
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
