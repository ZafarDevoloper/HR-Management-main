package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.EmployeeDto;
import com.example.hrmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public HttpEntity<?> get() {

        ApiResponse apiResponse = employeeService.get();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{employeeId}")
    public HttpEntity<?> getById(@PathVariable UUID employeeId) {

        ApiResponse apiResponse = employeeService.getById(employeeId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{employeeId}")
    public HttpEntity<?> edit(@PathVariable UUID employeeId, @RequestBody EmployeeDto employeeDto) {

        ApiResponse apiResponse = employeeService.edit(employeeId, employeeDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{employeeId}")
    public HttpEntity<?> delete(@PathVariable UUID employeeId) {

        ApiResponse apiResponse = employeeService.delete(employeeId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
