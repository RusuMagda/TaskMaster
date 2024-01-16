package com.backend.dam.ControllerTest;

import com.backend.dam.controller.TaskController;
import com.backend.dam.dto.TaskDto;
import com.backend.dam.model.Status;
import com.backend.dam.service.TaskService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;


    @Test
    @WithMockUser(username = "test", password = "test")
    void getAllTasks() {
        List<TaskDto> mockTasks = Arrays.asList(new TaskDto(), new TaskDto());
        when(taskService.getAllTasks()).thenReturn(mockTasks);
        ResponseEntity<List<TaskDto>> responseEntity = taskController.getAllTasks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockTasks, responseEntity.getBody());
    }

    @Test
    void createTask() {

        TaskDto inputTaskDto = new TaskDto();
        inputTaskDto.setName("Test Task");

        TaskDto savedTaskDto = new TaskDto();
        savedTaskDto.setId(1L);
        savedTaskDto.setName("Test Task");

        when(taskService.createTask(inputTaskDto)).thenReturn(savedTaskDto);

        ResponseEntity<TaskDto> responseEntity = taskController.createTask(inputTaskDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedTaskDto, responseEntity.getBody());

        verify(taskService, times(1)).createTask(inputTaskDto);
    }

    @Test
    void getTaskById() {

        Long taskId = 1L;


        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskId);
        taskDto.setName("Test Task");

        when(taskService.getTaskById(taskId)).thenReturn(taskDto);

        ResponseEntity<TaskDto> responseEntity = taskController.getTaskById(taskId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(taskDto, responseEntity.getBody());

        verify(taskService, times(1)).getTaskById(taskId);
    }
    @Test
    void updateTask() {
        Long taskId = 1L;
        TaskDto updatedTask = new TaskDto();
        updatedTask.setName("Updated Task");

        TaskDto taskDto = new TaskDto();
        taskDto.setId(taskId);
        taskDto.setName("Updated Task");

        when(taskService.updateTask(taskId, updatedTask)).thenReturn(taskDto);

        ResponseEntity<TaskDto> responseEntity = taskController.updateTask(taskId, updatedTask);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(taskDto, responseEntity.getBody());

        verify(taskService, times(1)).updateTask(taskId, updatedTask);
    }

    @Test
    void deleteTask() {
        Long taskId = 1L;

        ResponseEntity<String> responseEntity = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Task deleted successfully!", responseEntity.getBody());

        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    void getAllStatuses() {
        ResponseEntity<Status[]> responseEntity = taskController.getAllStatuses();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertArrayEquals(Status.values(), responseEntity.getBody());
    }

    @Test
    void addUserToProject() {
        Long userId = 1L;
        Long taskId = 2L;

        taskController.addUserToProject(userId, taskId);

        verify(taskService, times(1)).addUserToTask(taskId, userId);
    }
}