package com.taskflow.backend.controller;

import com.taskflow.backend.dto.TaskRequest;
import com.taskflow.backend.model.Task;
import com.taskflow.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private Long userId(Authentication auth) {
        return (Long) auth.getPrincipal();
    }

    @GetMapping
    public List<Task> getTasks(Authentication auth) {
        return taskService.getTasks(userId(auth));
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody TaskRequest req, Authentication auth) {
        return ResponseEntity.ok(taskService.createTask(userId(auth), req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody TaskRequest req, Authentication auth) {
        return ResponseEntity.ok(taskService.updateTask(userId(auth), id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        taskService.deleteTask(userId(auth), id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<Task> toggle(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(taskService.toggleDone(userId(auth), id));
    }
}