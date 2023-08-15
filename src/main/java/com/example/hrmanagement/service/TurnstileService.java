package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Turnstile;
import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.TurnstileDto;
import com.example.hrmanagement.repository.TurnstileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TurnstileService {

    @Autowired
    TurnstileRepository turnstileRepository;

    public ApiResponse add(TurnstileDto turnstileDto) {

        boolean existsByName = turnstileRepository.existsByName(turnstileDto.getName());
        if (existsByName) {
            return new ApiResponse("Such a name already exists in the system", false);
        }
        Turnstile turnstile = new Turnstile();
        turnstile.setName(turnstileDto.getName());
        turnstileRepository.save(turnstile);
        return new ApiResponse("Turnstile created", true);
    }

    public ApiResponse get() {

        List<Turnstile> turnstileList = turnstileRepository.findAll();
        return new ApiResponse("Success", true, turnstileList);
    }

    public ApiResponse getById(UUID turnstileId) {
        Optional<Turnstile> optionalTurnstile = turnstileRepository.findById(turnstileId);
        if (optionalTurnstile.isEmpty()) {
            return new ApiResponse("Turnstile not found", false);
        }
        return new ApiResponse("Success", true, optionalTurnstile);
    }

    public ApiResponse edit(UUID turnstileId, TurnstileDto turnstileDto) {

        Optional<Turnstile> optionalTurnstile = turnstileRepository.findById(turnstileId);
        if (optionalTurnstile.isEmpty()) {
            return new ApiResponse("Turnstile not found", false);
        }

        boolean existsByName = turnstileRepository.existsByName(turnstileDto.getName());
        if (existsByName) {
            return new ApiResponse("Such a name already exists in the system", false);
        }
        Turnstile editingTurnstile = optionalTurnstile.get();
        editingTurnstile.setName(turnstileDto.getName());
        turnstileRepository.save(editingTurnstile);
        return new ApiResponse("Turnstile edited", true);
    }

    public ApiResponse delete(UUID turnstileId) {

        Optional<Turnstile> optionalTurnstile = turnstileRepository.findById(turnstileId);
        if (optionalTurnstile.isEmpty()) {
            return new ApiResponse("Turnstile not found", false);
        }
        turnstileRepository.deleteById(turnstileId);
        return new ApiResponse("Success", true);
    }
}
