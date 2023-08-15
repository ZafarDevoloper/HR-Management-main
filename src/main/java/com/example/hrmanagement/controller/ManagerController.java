package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.ManagerDto;
import com.example.hrmanagement.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping
    public HttpEntity<?> get() {

        ApiResponse apiResponse = managerService.get();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{managerId}")
    public HttpEntity<?> getById(@PathVariable UUID managerId) {

        ApiResponse apiResponse = managerService.getById(managerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{managerId}")
    public HttpEntity<?> edit(@PathVariable UUID managerId, @RequestBody ManagerDto managerDto) {

        ApiResponse apiResponse = managerService.edit(managerId, managerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{managerId}")
    public HttpEntity<?> delete(@PathVariable UUID managerId) {

        ApiResponse apiResponse = managerService.delete(managerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
