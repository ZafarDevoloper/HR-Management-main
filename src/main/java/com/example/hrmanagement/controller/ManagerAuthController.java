package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.ManagerLoginDto;
import com.example.hrmanagement.payload.ManagerRegisterDto;
import com.example.hrmanagement.service.ManagerAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managerAuth")
public class ManagerAuthController {

    @Autowired
    ManagerAuthService managerAuthService;

    @PostMapping("/register")
    public HttpEntity<?> registerManager(@RequestBody ManagerRegisterDto managerRegisterDto) {

        ApiResponse apiResponse = managerAuthService.registerManager(managerRegisterDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);

    }

    @GetMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam String email) {
        ApiResponse apiResponse = managerAuthService.verifyEmail(emailCode, email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginManager(@RequestBody ManagerLoginDto managerLoginDto) {
        ApiResponse apiResponse = managerAuthService.login(managerLoginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }
}
