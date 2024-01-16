package com.backend.dam.ServiceTest;

import com.backend.dam.dto.ProjectDto;
import com.backend.dam.mapper.ProjectMapper;
import com.backend.dam.model.Project;
import com.backend.dam.model.User;
import com.backend.dam.repository.ProjectRepository;
import com.backend.dam.repository.UserRepository;
import com.backend.dam.service.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.security.test.context.support.WithMockUser;
@SpringBootTest
@WithMockUser(username = "testuser", roles = "USER")
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private ProjectDto projectDto;
    private Project project;
    private User user;

    @BeforeEach
    public void setUp() {
        projectDto = new ProjectDto("Test Project", "Description", LocalDate.now());
        project = ProjectMapper.mapToProject(projectDto);
        user = new User();
        user.setId(1L);
    }

    @Test
    public void testCreateProject() {
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDto createdProjectDto = projectService.createProject(projectDto);

        assertNotNull(createdProjectDto);
        assertEquals(projectDto.getTitle(), createdProjectDto.getTitle());
        assertEquals(projectDto.getDescription(), createdProjectDto.getDescription());
    }

    @Test
    public void testGetProjectById() {
        Long projectId = 1L;
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        ProjectDto retrievedProjectDto = projectService.getProjectById(projectId);

        assertNotNull(retrievedProjectDto);
        assertEquals(project.getTitle(), retrievedProjectDto.getTitle());

    }

    @Test
    public void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(project);
        projects.add(new Project());

        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        List<ProjectDto> projectDtos = projectService.getAllProjects();

        assertNotNull(projectDtos);
        assertEquals(projects.size(), projectDtos.size());

    }

    @Test
    public void testUpdateProject() {
        Long projectId = 1L;
        ProjectDto updatedProjectDto = new ProjectDto("Updated Project", "Updated Description", LocalDate.now());

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectDto resultProjectDto = projectService.updateProject(projectId, updatedProjectDto);

        assertNotNull(resultProjectDto);
        assertEquals(updatedProjectDto.getTitle(), resultProjectDto.getTitle());
        assertEquals(updatedProjectDto.getDescription(), resultProjectDto.getDescription());

    }

    @Test
    public void testDeleteProject() {
        Long projectId = 1L;
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        assertDoesNotThrow(() -> projectService.deleteProject(projectId));
    }

    @Test
    public void testAddUserToProject() {
        Long projectId = 1L;
        Long userId = 1L;

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);

        assertDoesNotThrow(() -> projectService.addUserToProject(projectId, userId));
    }

    @Test
    public void testRemoveUserFromProject() {
        Long projectId = 1L;
        Long userId = 1L;

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(projectRepository.save(any(Project.class))).thenReturn(project);

        assertDoesNotThrow(() -> projectService.removeUserFromProject(projectId, userId));
    }
}
