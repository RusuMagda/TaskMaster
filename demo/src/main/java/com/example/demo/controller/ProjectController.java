package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping(value="/projects")
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @PostMapping(value="/project")
    public Project createProject(@RequestBody Project project){
        return projectService.createProject(project);
    }
}
