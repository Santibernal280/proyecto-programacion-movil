package com.taskflow.backend.service;

import com.taskflow.backend.dto.TaskRequest;
import com.taskflow.backend.model.*;
import com.taskflow.backend.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    public TaskService(TaskRepository taskRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
    }

    public List<Task> getTasks(Long userId) {
        return taskRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Task createTask(Long userId, TaskRequest req) {
        User user = userRepo.findById(userId).orElseThrow();
        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority() != null ? req.getPriority() : "media");
        task.setDueDate(req.getDueDate());
        task.setUser(user);
        return taskRepo.save(task);
    }

    public Task updateTask(Long userId, Long taskId, TaskRequest req) {
        Task task = taskRepo.findById(taskId).orElseThrow();
        if (!task.getUser().getId().equals(userId))
            throw new RuntimeException("Sin permiso");
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setPriority(req.getPriority());
        task.setDueDate(req.getDueDate());
        return taskRepo.save(task);
    }

    public void deleteTask(Long userId, Long taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow();
        if (!task.getUser().getId().equals(userId))
            throw new RuntimeException("Sin permiso");
        taskRepo.delete(task);
    }

    public Task toggleDone(Long userId, Long taskId) {
        Task task = taskRepo.findById(taskId).orElseThrow();
        if (!task.getUser().getId().equals(userId))
            throw new RuntimeException("Sin permiso");
        task.setCompleted(!task.isCompleted());
        return taskRepo.save(task);
    }
}