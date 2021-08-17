package com.redarmdevs.taskswebapp.controllers;

import com.redarmdevs.taskswebapp.message.ResponseMessage;
import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping(path = "{user}")
    public Set<Task> getTasks(@PathVariable(name = "user") String user){
        return taskService.getTasks(user);
    }

    @PostMapping(path = "{user}")
    public void addTask(@PathVariable(name = "user") String username, @RequestBody Task task){
        taskService.addTask(username, task);
    }
}
