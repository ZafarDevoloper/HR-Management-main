package com.example.hrmanagement.controller;

import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.TurnstileDto;
import com.example.hrmanagement.service.TurnstileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/turnstile")
public class TurnstileController {

    @Autowired
    TurnstileService turnstileService;

    @PutMapping
    public HttpEntity<?> add(@RequestBody TurnstileDto turnstileDto) {

        ApiResponse apiResponse = turnstileService.add(turnstileDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> get() {

        ApiResponse apiResponse = turnstileService.get();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{turnstileId}")
    public HttpEntity<?> getById(@PathVariable UUID turnstileId) {

        ApiResponse apiResponse = turnstileService.getById(turnstileId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{turnstileId}")
    public HttpEntity<?> edit(@PathVariable UUID turnstileId, @RequestBody TurnstileDto turnstileDto) {

        ApiResponse apiResponse = turnstileService.edit(turnstileId, turnstileDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{turnstileId}")
    public HttpEntity<?> delete(@PathVariable UUID turnstileId) {

        ApiResponse apiResponse = turnstileService.delete(turnstileId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
