package com.redarmdevs.taskswebapp.services;

import com.redarmdevs.taskswebapp.exceptions.TaskException;
import com.redarmdevs.taskswebapp.exceptions.UserException;
import com.redarmdevs.taskswebapp.models.Task;
import com.redarmdevs.taskswebapp.models.User;
import com.redarmdevs.taskswebapp.repositories.TaskRepository;
import com.redarmdevs.taskswebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    public List<Task> getTasks(String username) throws UserException {
        User user = findUser(username);
        return user.getTasks();
    }

    public void addTask(String username, Task task) throws UserException {
        User user = findUser(username);
        task.setUser(user);
        taskRepo.save(task);
    }

    @Transactional
    public void updateTask(String username, Long taskID, Task task) {
        Task originalTask = findTask(taskID);
        User owner = findUser(username);
        if(!originalTask.getUser().equals(owner))
            throw new UserException("unauthorized");
        originalTask.setName(task.getName());
        originalTask.setDesc(task.getDesc());
        originalTask.setFrequency(task.getFrequency());
    }

    public void deleteTask(String username, Long taskID) throws TaskException{
        User user = findUser(username);
        Task task = findTask(taskID);
        try {
            taskRepo.delete(task);
        } catch (Exception e) {
            throw new TaskException("failed to delete task: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteAllTasksByUser(String username) throws UserException{
        User user = findUser(username);
        taskRepo.deleteAllByUser(user);
    }

    private User findUser(String username){
        Optional<User> user = userRepo.findUserByUsername(username);
        if(user.isEmpty())
            throw new TaskException("username not found.");
        return user.get();
    }

    private Task findTask(Long taskID){
        Optional<Task> task = taskRepo.findById(taskID);
        if(task.isEmpty())
            throw new TaskException("Task ID " + taskID + " not found.");
        return task.get();
    }

    @Transactional
    public void completeTask(String username, Long taskID) {
        User user = findUser(username);
        Task task = findTask(taskID);
        if(!task.getUser().equals(user))
            throw new UserException("unauthorized");
        task.setLastTime(LocalDateTime.now());
    }
}
