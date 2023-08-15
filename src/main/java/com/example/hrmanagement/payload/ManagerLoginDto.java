package com.example.hrmanagement.payload;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ManagerLoginDto {

    @NotNull
    @Email
    private String username;

    @NotNull
    private String password;

}
