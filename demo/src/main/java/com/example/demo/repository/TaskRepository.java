package com.backend.dam.repository;

import com.backend.dam.model.Status;
import com.backend.dam.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByUsersEmail(String email);
    List<Task> findByUsersEmailAndStatus(String userEmail, Status status);
}
