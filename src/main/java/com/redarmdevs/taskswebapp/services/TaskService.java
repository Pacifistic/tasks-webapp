package com.redarmdevs.taskswebapp.services;

import com.redarmdevs.taskswebapp.exceptions.TaskException;
import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.models.User;
import com.redarmdevs.taskswebapp.repositories.TaskRepository;
import com.redarmdevs.taskswebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    public Set<Task> getTasks(String username) throws UserException {
        Optional<User> user = userRepo.findUserByUsername(username);
        if(user.isEmpty())
            throw new UserException("username not found");
        return user.get().getTasks();
    }

    public void addTask(String username, Task task) throws TaskException {
        Optional<User> user = userRepo.findUserByUsername(username);
        if(user.isEmpty())
            throw new TaskException("username not found");
        task.setUser(user.get());
        taskRepo.save(task);
    }
}
