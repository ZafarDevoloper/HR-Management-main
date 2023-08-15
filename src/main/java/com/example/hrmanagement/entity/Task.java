package com.example.hrmanagement.entity;

import com.example.hrmanagement.entity.enums.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Timestamp deadLine;

    @Enumerated(EnumType.STRING)
    private TaskState state;

    @ManyToOne
    private Employee responsibleEmployee;


}
