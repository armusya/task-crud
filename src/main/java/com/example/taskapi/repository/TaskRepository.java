package com.example.taskapi.repository;

import com.example.taskapi.model.Status;
import com.example.taskapi.model.Task;
import com.example.taskapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOwnerEmail(String email);
    List<Task> findAllByOwnerAndStatus(User owner, Status status);
}
