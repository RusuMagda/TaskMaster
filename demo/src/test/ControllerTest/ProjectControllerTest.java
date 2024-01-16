package com.backend.dam.ControllerTest;

import com.backend.dam.controller.ProjectController;
import com.backend.dam.dto.ProjectDto;
import com.backend.dam.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {
    @InjectMocks
    private ProjectController projectController;
    @Mock
    private ProjectService projectService;

    @Test
    void createProject() {
        ProjectDto inputProjectDto = new ProjectDto();
        inputProjectDto.setTitle("Test Project");

        ProjectDto savedProjectDto = new ProjectDto();
        savedProjectDto.setId(1L);
        savedProjectDto.setTitle("Test Project");

        when(projectService.createProject(inputProjectDto)).thenReturn(savedProjectDto);

        ResponseEntity<ProjectDto> responseEntity = projectController.createProject(inputProjectDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedProjectDto, responseEntity.getBody());

        verify(projectService, times(1)).createProject(inputProjectDto);
    }

    @Test
    void getProjectById() {
        Long projectId = 1L;

        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(projectId);
        projectDto.setTitle("Test Project");

        when(projectService.getProjectById(projectId)).thenReturn(projectDto);

        ResponseEntity<ProjectDto> responseEntity = projectController.getProjectById(projectId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projectDto, responseEntity.getBody());

        verify(projectService, times(1)).getProjectById(projectId);
    }

    @Test
    void getAllProjects() {
        List<ProjectDto> projects = Arrays.asList(new ProjectDto(), new ProjectDto());

        when(projectService.getAllProjects()).thenReturn(projects);

        ResponseEntity<List<ProjectDto>> responseEntity = projectController.getAllProjects();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projects, responseEntity.getBody());

        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void updateProject() {
        // Mock input
        Long projectId = 1L;
        ProjectDto updatedProject = new ProjectDto();
        updatedProject.setTitle("Updated Project");

        // Mock output
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(projectId);
        projectDto.setTitle("Updated Project");

        when(projectService.updateProject(projectId, updatedProject)).thenReturn(projectDto);

        ResponseEntity<ProjectDto> responseEntity = projectController.updateProject(projectId, updatedProject);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(projectDto, responseEntity.getBody());

        verify(projectService, times(1)).updateProject(projectId, updatedProject);
    }

    @Test
    void deleteProject() {
        Long projectId = 1L;

        ResponseEntity<String> responseEntity = projectController.deleteProject(projectId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Project deleted successfully!", responseEntity.getBody());

        verify(projectService, times(1)).deleteProject(projectId);
    }

    @Test
    void addUserToProject() {
        Long userId = 1L;
        Long projectId = 2L;

        projectController.addUserToProject(userId, projectId);

        verify(projectService, times(1)).addUserToProject(projectId, userId);
    }

    @Test
    void removeUserFromProject() {
        Long userId = 1L;
        Long projectId = 2L;

        projectController.removeUserFromProject(userId, projectId);

        verify(projectService, times(1)).removeUserFromProject(projectId, userId);
    }
}
