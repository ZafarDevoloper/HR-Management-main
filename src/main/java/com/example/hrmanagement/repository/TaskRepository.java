package com.example.hrmanagement.repository;

import com.example.hrmanagement.entity.Task;
import com.example.hrmanagement.entity.enums.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> getTaskByStateAndResponsibleEmployeeId(TaskState state, UUID responsibleId);
}
