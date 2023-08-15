package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.TaskDto;
import com.example.hrmanagement.payload.TaskSetStateDto;
import com.example.hrmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;


    @PutMapping
    public HttpEntity<?> add(@RequestBody TaskDto taskDto) {

        ApiResponse apiResponse = taskService.add(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping
    public HttpEntity<?> get(@RequestParam Optional<String> state, @RequestParam Optional<UUID> employeeId) {

        ApiResponse apiResponse = taskService.get(state, employeeId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{taskId}")
    public HttpEntity<?> getById(@PathVariable UUID taskId) {

        ApiResponse apiResponse = taskService.getById(taskId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{taskId}")
    public HttpEntity<?> edit(@PathVariable UUID taskId, @RequestBody TaskDto taskDto) {

        ApiResponse apiResponse = taskService.edit(taskId, taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PatchMapping("/{taskId}")
    public HttpEntity<?> setState(@PathVariable UUID taskId, @RequestBody TaskSetStateDto taskSetStateDto) {

        ApiResponse apiResponse = taskService.setState(taskId, taskSetStateDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{taskId}")
    public HttpEntity<?> delete(@PathVariable UUID taskId) {

        ApiResponse apiResponse = taskService.delete(taskId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
