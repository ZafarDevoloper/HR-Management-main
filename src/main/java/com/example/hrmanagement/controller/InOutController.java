package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.InOutDto;
import com.example.hrmanagement.service.InOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inOut")
public class InOutController {

    @Autowired
    InOutService inOutService;

    @PutMapping
    public HttpEntity<?> add(@RequestBody InOutDto inOutDto) {

        ApiResponse apiResponse = inOutService.add(inOutDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> get() {

        ApiResponse apiResponse = inOutService.get();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{inOutId}")
    public HttpEntity<?> getById(@PathVariable UUID inOutId) {

        ApiResponse apiResponse = inOutService.getById(inOutId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{inOutId}")
    public HttpEntity<?> delete(@PathVariable UUID inOutId) {

        ApiResponse apiResponse = inOutService.delete(inOutId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
