package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(value="/tasks")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @PostMapping(value="/task")
    public Task createTask(@RequestBody Task task){
        return taskService.createTask(task);
    }


}
