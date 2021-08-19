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

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping(path = "/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTasks(Principal principal){
        try {
            return ResponseEntity.ok(taskService.getTasks(principal.getName()));
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping(path = "/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task,
                                          Principal principal){
        try {
            taskService.addTask(principal.getName(), task);
            return ResponseEntity.ok(new MessageResponse("task '" + task.getName() + "' added successfully!"));
        }
        catch (UserException e){
            return ResponseEntity.badRequest().body(new MessageResponse("task failed to be added: " + e.getMessage()));
        }
    }

    @DeleteMapping(path = {"/user"})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteTask(@RequestParam(name = "taskID") Long taskID,
                                             Principal principal){
        try{
            taskService.deleteTask(principal.getName(), taskID);
            return ResponseEntity.ok(new MessageResponse("task deleted successfully"));
        } catch (TaskException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping(path = "user/deleteTasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteAllUsersTasks(Principal principal){
        try{
            taskService.deleteAllTasksByUser(principal.getName());
            return ResponseEntity.ok(new MessageResponse("All tasks by " + principal.getName() + " deleted successfully"));
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
