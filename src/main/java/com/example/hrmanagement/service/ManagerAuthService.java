package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Manager;
import com.example.hrmanagement.entity.enums.RoleName;
import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.ManagerLoginDto;
import com.example.hrmanagement.payload.ManagerRegisterDto;
import com.example.hrmanagement.repository.ManagerRepository;
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
public class ManagerAuthService implements UserDetailsService {

    @Autowired
    ManagerRepository managerRepository;

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


    public ApiResponse registerManager(ManagerRegisterDto managerRegisterDto) {

        boolean existByEmail = managerRepository.existsByEmail(managerRegisterDto.getEmail());
        if (existByEmail) {
            return new ApiResponse("Bunday email tizimda allaqachon mavjud", false);
        }
        Manager manager = new Manager();
        manager.setFirstName(managerRegisterDto.getFirstName());
        manager.setLastName(managerRegisterDto.getLastName());
        manager.setEmail(managerRegisterDto.getEmail());
        manager.setPassword(passwordEncoder.encode(managerRegisterDto.getPassword()));
        manager.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_MANAGER)));
        manager.setEmailCode(UUID.randomUUID().toString());
        managerRepository.save(manager);
        try {
            sendEmail(manager.getEmail(), manager.getEmailCode());
            return new ApiResponse("Successfully registered", true);
        } catch (Exception e) {
            return new ApiResponse("Fail", false);
        }
    }

    public void sendEmail(String sendingEmail, String emailCode) throws Exception {
        mailService.sendEmail(
                sendingEmail,
                "Verification Email",
                "<a href='http://localhost:8080/api/managerAuth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Verify Email</a>"
        );
    }

    public ApiResponse verifyEmail(String emailCode, String email) {

        Optional<Manager> optionalManager = managerRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            manager.setEnabled(true);
            manager.setEmailCode(null);
            managerRepository.save(manager);
            return new ApiResponse("Account verified", true);
        }

        return new ApiResponse("Account already exist", false);
    }

    public ApiResponse login(ManagerLoginDto managerLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    managerLoginDto.getUsername(),
                    managerLoginDto.getPassword()));
            Manager manager = (Manager) authentication.getPrincipal();
            String token = jwtProvider.generateToken(managerLoginDto.getUsername(), manager.getRoles());
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Login or Password is incorrect", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Optional<User> optionalUser = userRepository.findByEmail(username);
//        if (optionalUser.isPresent())
//            return optionalUser.get();
//            throw new UsernameNotFoundException(username + "not found");

        return managerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
    }
}
