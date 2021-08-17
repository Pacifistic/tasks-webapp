package com.redarmdevs.taskswebapp.services;

import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.models.User;
import com.redarmdevs.taskswebapp.repositories.TaskRepository;
import com.redarmdevs.taskswebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    public Set<Task> getTasks(String username) {
        User user = userRepo.findUserByUsername(username);
        //return taskRepo.findTasksByUser(user);
        return user.getTasks();
    }

    public void addTask(String username, Task task) {
        User user = userRepo.findUserByUsername(username);
        task.setUser(user);
        taskRepo.save(task);
    }
}
