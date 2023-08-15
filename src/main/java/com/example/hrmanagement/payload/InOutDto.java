package com.example.hrmanagement.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Data
public class InOutDto {

    @NotNull
    private String type;

    @NotNull
    private UUID employeeId;

    @NotNull
    private UUID turnstileId;

}
