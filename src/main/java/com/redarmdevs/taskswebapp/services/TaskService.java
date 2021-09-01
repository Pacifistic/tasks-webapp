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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
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
        List<Task> tasks = user.getTasks();
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.getFrequency().equals(Duration.ZERO) && o2.getFrequency().equals(Duration.ZERO)) {
                    if (o1.getLastTime() == null && o2.getLastTime() == null)
                        return 0;
                    else if (o1.getLastTime() == null)
                        return -1;
                    else if (o2.getLastTime() == null)
                        return 1;
                }
                else if(o1.getFrequency().equals(Duration.ZERO) && !o2.getFrequency().equals(Duration.ZERO))
                    return 1;
                else if(!o1.getFrequency().equals(Duration.ZERO) && o2.getFrequency().equals(Duration.ZERO))
                    return -1;
                else if(o1.getLastTime() == null && o2.getLastTime() == null)
                    return o1.getFrequency().compareTo(o2.getFrequency());
                else if(o1.getLastTime() == null)
                    return -1;
                else if(o2.getLastTime() == null)
                    return 1;
                return o1.getLastTime().plus(o1.getFrequency()).compareTo(o2.getLastTime().plus(o2.getFrequency()));
            }
        });
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
