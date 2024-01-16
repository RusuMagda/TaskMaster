package com.backend.dam.ServiceTest;

import com.backend.dam.dto.TaskDto;
import com.backend.dam.mapper.TaskMapper;
import com.backend.dam.model.Task;
import com.backend.dam.model.User;
import com.backend.dam.repository.TaskRepository;
import com.backend.dam.repository.UserRepository;
import com.backend.dam.service.TaskServiceImpl;
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

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private TaskDto taskDto;
    private Task task;
    private User user;

    @BeforeEach
    public void setUp() {
        taskDto = new TaskDto("Test Task", "Description", LocalDate.now(),2);
        task = TaskMapper.mapToTask(taskDto);
        user = new User();
        user.setId(1L);
    }

    @Test
    public void testCreateTask() {
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDto createdTaskDto = taskService.createTask(taskDto);

        assertNotNull(createdTaskDto);
        assertEquals(taskDto.getName(), createdTaskDto.getName());
        assertEquals(taskDto.getDescription(), createdTaskDto.getDescription());

    }

    @Test
    public void testGetTaskById() {
        Long taskId = 1L;
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskDto retrievedTaskDto = taskService.getTaskById(taskId);

        assertNotNull(retrievedTaskDto);
        assertEquals(task.getName(), retrievedTaskDto.getName());

    }

    @Test
    public void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(new Task());

        Mockito.when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDto> taskDtos = taskService.getAllTasks();

        assertNotNull(taskDtos);
        assertEquals(tasks.size(), taskDtos.size());

    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        TaskDto updatedTaskDto = new TaskDto("Updated Task", "Updated Description", LocalDate.now(),4);

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDto resultTaskDto = taskService.updateTask(taskId, updatedTaskDto);

        assertNotNull(resultTaskDto);
        assertEquals(updatedTaskDto.getName(), resultTaskDto.getName());
        assertEquals(updatedTaskDto.getDescription(), resultTaskDto.getDescription());

    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertDoesNotThrow(() -> taskService.deleteTask(taskId));
    }

    @Test
    public void testAddUserToTask() {
        Long taskId = 1L;
        Long userId = 1L;

        Mockito.when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertDoesNotThrow(() -> taskService.addUserToTask(taskId, userId));
    }
}
