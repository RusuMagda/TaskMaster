package com.example.demo.service;



import com.example.demo.model.Project;

import java.awt.*;
import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects();

    Project createProject(Project project);
}
