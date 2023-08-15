package com.example.hrmanagement.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class TaskDto {

    @NotNull
    private String name;

    private Timestamp deadLine;

    @NotNull
    private String state;

    private UUID employeeId;
}
