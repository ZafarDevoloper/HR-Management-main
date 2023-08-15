package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Manager;
import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.ManagerDto;
import com.example.hrmanagement.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ManagerService {

    @Autowired
    ManagerRepository managerRepository;

    public ApiResponse get() {

        List<Manager> managerList = managerRepository.findAll();
        return new ApiResponse("Success", true, managerList);

    }

    public ApiResponse getById(UUID managerId) {
        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isEmpty()) {
            return new ApiResponse("Manger not found", false);
        }
        return new ApiResponse("Success", true, optionalManager);
    }

    public ApiResponse edit(UUID mangerId, ManagerDto managerDto) {

        Optional<Manager> optionalManager = managerRepository.findById(mangerId);
        if (optionalManager.isEmpty()) {

            return new ApiResponse("Manager not found", false);

        }

        boolean existByEmail = managerRepository.existsByEmail(managerDto.getEmail());
        if (existByEmail) {
            return new ApiResponse("Such an email already exists in the system", false);
        }
        Manager editingManager = optionalManager.get();
        editingManager.setFirstName(managerDto.getFirstName());
        editingManager.setLastName(managerDto.getLastName());
        editingManager.setEmail(managerDto.getEmail());
        editingManager.setPassword(managerDto.getPassword());
        editingManager.setSalary(managerDto.getSalary());
        managerRepository.save(editingManager);
        return new ApiResponse("Manager edited", true);
    }

    public ApiResponse delete(UUID managerId) {

        Optional<Manager> optionalManager = managerRepository.findById(managerId);
        if (optionalManager.isEmpty()) {
            return new ApiResponse("Manager not found", false);
        }
        managerRepository.deleteById(managerId);
        return new ApiResponse("Manager deleted", true);
    }

}
