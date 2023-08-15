package com.example.hrmanagement.payload;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class TaskSetStateDto {

    @NotNull
    private String state;

}
