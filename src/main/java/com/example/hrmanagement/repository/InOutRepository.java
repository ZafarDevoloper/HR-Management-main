package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.InOut;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InOutRepository extends JpaRepository<InOut, UUID> {

}
