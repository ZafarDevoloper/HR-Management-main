package com.example.hrmanagement.entity;

import com.example.hrmanagement.entity.enums.InOutType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InOut {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private InOutType type;

    @OneToOne
    private Employee employee;

    @ManyToOne
    private Turnstile turnstile;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp timestamp;
}
