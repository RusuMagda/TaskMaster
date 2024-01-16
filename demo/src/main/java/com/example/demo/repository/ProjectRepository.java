package com.backend.dam.repository;

import com.backend.dam.model.Project;
import com.backend.dam.model.Status;
import com.backend.dam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByStatus(Status status);
}
