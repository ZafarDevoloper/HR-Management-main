package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Employee;
import com.example.hrmanagement.entity.InOut;
import com.example.hrmanagement.entity.Turnstile;
import com.example.hrmanagement.entity.enums.InOutType;
import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.InOutDto;
import com.example.hrmanagement.repository.EmployeeRepository;
import com.example.hrmanagement.repository.InOutRepository;
import com.example.hrmanagement.repository.TurnstileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InOutService {

    @Autowired
    InOutRepository inOutRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TurnstileRepository turnstileRepository;

    public ApiResponse add(InOutDto inOutDto) {

        InOut inOut = new InOut();
        inOut.setType(InOutType.getType(inOutDto.getType()));

        Optional<Employee> optionalEmployee = employeeRepository.findById(inOutDto.getEmployeeId());
        if (optionalEmployee.isEmpty()) {
            return new ApiResponse("Employee not found", false);
        }

        inOut.setEmployee(optionalEmployee.get());

        Optional<Turnstile> optionalTurnstile = turnstileRepository.findById(inOutDto.getTurnstileId());
        if (optionalTurnstile.isEmpty()) {
            return new ApiResponse("Turnstile not found", false);
        }

        inOut.setTurnstile(optionalTurnstile.get());
        inOutRepository.save(inOut);

        return new ApiResponse("InOut recorded", true);
    }

    public ApiResponse get() {

        List<InOut> inOutList = inOutRepository.findAll();
        return new ApiResponse("Success", true, inOutList);
    }

    public ApiResponse getById(UUID inOutId) {

        Optional<InOut> optionalInOut = inOutRepository.findById(inOutId);
        if (optionalInOut.isEmpty()) {
            return new ApiResponse("InOut record not found", false);
        }
        return new ApiResponse("Success", true, optionalInOut);
    }

    public ApiResponse delete(UUID inOutId) {

        Optional<InOut> optionalInOut = inOutRepository.findById(inOutId);
        if (optionalInOut.isEmpty()) {
            return new ApiResponse("InOut record not found", false);
        }
        inOutRepository.deleteById(inOutId);
        return new ApiResponse("Success", true);
    }

}
