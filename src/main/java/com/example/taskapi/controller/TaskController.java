package com.example.taskapi.controller;

import com.example.taskapi.model.Status;
import com.example.taskapi.model.Task;
import com.example.taskapi.service.TaskService;
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


    @GetMapping
    public ResponseEntity<List<Task>> getAll(@RequestParam(required = false) Status status, Authentication authentication) {
        String email = authentication.getName();
        List<Task> tasks = (status != null)
                ? taskService.findByStatus(email, status)
                : taskService.findAllByOwnerEmail(email);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getOne(@PathVariable Long id,
                                       Authentication authentication) {
        String email = authentication.getName();
        return taskService.findByIdAndOwnerEmail(id, email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task,
                                       Authentication authentication) {
        String email = authentication.getName();
        Task created = taskService.create(task, email);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id,
                                       @RequestBody Task updated,
                                       Authentication authentication) {
        String email = authentication.getName();
        return taskService.update(id, updated, email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Task> partialUpdate(@PathVariable Long id,
                                              @RequestBody Task partialUpdate,
                                              Authentication authentication) {
        String email = authentication.getName();
        return taskService.partialUpdate(id, partialUpdate, email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       Authentication authentication) {
        String email = authentication.getName();
        boolean deleted = taskService.delete(id, email);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
