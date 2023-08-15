package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Turnstile;
import org.springframework.data.jpa.repository.JpaRepository;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface TurnstileRepository extends JpaRepository<Turnstile, UUID> {

    boolean existsByName(@NotNull String name);
}
