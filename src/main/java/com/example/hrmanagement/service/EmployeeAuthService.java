package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Employee;
import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.EmployeeLoginDto;
import com.example.hrmanagement.payload.EmployeeRegisterDto;
import com.example.hrmanagement.payload.SetPasswordDto;
import com.example.hrmanagement.repository.EmployeeRepository;
import com.example.hrmanagement.repository.RoleRepository;
import com.example.hrmanagement.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeAuthService implements UserDetailsService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MailService mailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerEmployee(EmployeeRegisterDto employeeRegisterDto) {

        boolean existByEmail = employeeRepository.existsByEmail(employeeRegisterDto.getEmail());
        if (existByEmail) {
            return new ApiResponse("Such an email already exists in the system", false);
        }
        Employee employee = new Employee();
        employee.setFirstName(employeeRegisterDto.getFirstName());
        employee.setLastName(employeeRegisterDto.getLastName());
        employee.setEmail(employeeRegisterDto.getEmail());
        employee.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_EMPLOYEE)));
        employee.setEmailCode(UUID.randomUUID().toString());
        employeeRepository.save(employee);
        try {
            sendEmail(employee.getEmail());
            return new ApiResponse("Successfully registered", true);
        } catch (Exception e) {
            return new ApiResponse("Fail", false);
        }
    }

    public void sendEmail(String sendingEmail) throws Exception {
        mailService.sendEmail(
                sendingEmail,
                "Set password",
                "<a href='http://localhost:8080/api/employeeAuth/password-set?email=" + sendingEmail + "'>Set Password</a>"
        );
    }

    public ApiResponse setPassword(SetPasswordDto setPasswordDto) {

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(setPasswordDto.getEmail());
        if (optionalEmployee.isEmpty()) {
            return new ApiResponse("Employee not found", false);
        }
        Employee employee = optionalEmployee.get();
        employee.setPassword(setPasswordDto.getPassword());
        employeeRepository.save(employee);
        return new ApiResponse("Success", true, employee);
    }

    public ApiResponse login(EmployeeLoginDto employeeLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    employeeLoginDto.getUsername(),
                    employeeLoginDto.getPassword()));
            Employee employee = (Employee) authentication.getPrincipal();
            String token = jwtProvider.generateToken(employeeLoginDto.getUsername(), employee.getRoles());
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Login or Password is incorrect", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }

}
