package com.example.taskapi.service;

import com.example.taskapi.model.Status;
import com.example.taskapi.model.Task;
import com.example.taskapi.model.User;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> findAllByOwnerEmail(String email) {
        return taskRepository.findAllByOwnerEmail(email);
    }

    public Optional<Task> findByIdAndOwnerEmail(Long id, String email) {
        return taskRepository.findById(id)
                .filter(task -> task.getOwner().getEmail().equals(email));
    }

    public Task create(Task task, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        task.setOwner(owner);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Optional<Task> update(Long id, Task updated, String ownerEmail) {
        return findByIdAndOwnerEmail(id, ownerEmail).map(task -> {
            task.setTitle(updated.getTitle());
            task.setDescription(updated.getDescription());
            task.setStatus(updated.getStatus());
            task.setUpdatedAt(LocalDateTime.now());
            return taskRepository.save(task);
        });
    }

    public boolean delete(Long id, String ownerEmail) {
        return findByIdAndOwnerEmail(id, ownerEmail).map(task -> {
            taskRepository.delete(task);
            return true;
        }).orElse(false);
    }
    public List<Task> findByStatus(String email, Status status){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return taskRepository.findAllByOwnerAndStatus(user,status);
    }
    public Optional<Task> partialUpdate(Long id, Task partialUpdate, String email) {
        return findByIdAndOwnerEmail(id, email).map(task ->{
            if(partialUpdate.getTitle() != null){
                task.setTitle(partialUpdate.getTitle());
            }
            if(partialUpdate.getDescription() != null){
                task.setDescription(partialUpdate.getDescription());
            }
            if(partialUpdate.getStatus() != null){
                task.setStatus(partialUpdate.getStatus());
            }
            return taskRepository.save(task);
        });

    }
}
