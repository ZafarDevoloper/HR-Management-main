package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.Employee;
import com.example.hrmanagement.entity.Task;
import com.example.hrmanagement.entity.enums.TaskState;
import com.example.hrmanagement.payload.ApiResponse;
import com.example.hrmanagement.payload.TaskDto;
import com.example.hrmanagement.payload.TaskSetStateDto;
import com.example.hrmanagement.repository.EmployeeRepository;
import com.example.hrmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MailService mailService;

    public ApiResponse add(TaskDto taskDto) {

        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDeadLine(taskDto.getDeadLine());
        task.setState(TaskState.getState(taskDto.getState()));

        Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
        if (optionalEmployee.isEmpty()) {
            return new ApiResponse("Employee not found", false);
        }
        task.setResponsibleEmployee(optionalEmployee.get());
        taskRepository.save(task);

        try {
            mailService.sendEmail(
                    optionalEmployee.get().getEmail(),
                    "Task",
                    "You have new task: " + task.getName()
            );
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        return new ApiResponse("Task added", true);
    }

    public ApiResponse get(Optional<String> state, Optional<UUID> employeeId) {
        List<Task> tasks = taskRepository.getTaskByStateAndResponsibleEmployeeId(TaskState.getState(state.orElse("")), employeeId.orElse(null));
        return new ApiResponse("Success", true, tasks);
    }

    public ApiResponse getById(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
            return new ApiResponse("Success", true, optionalTask);
    }

    public ApiResponse edit(UUID taskId, TaskDto taskDto) {

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        Task editingTask = optionalTask.get();
        editingTask.setName(taskDto.getName());
        editingTask.setDeadLine(taskDto.getDeadLine());
        editingTask.setState(TaskState.getState(taskDto.getState()));

        Optional<Employee> optionalEmployee = employeeRepository.findById(taskDto.getEmployeeId());
        if (optionalEmployee.isEmpty()) {
            return new ApiResponse("Employee not found", false);
        }
        editingTask.setResponsibleEmployee(optionalEmployee.get());
        taskRepository.save(editingTask);

        return new ApiResponse("Task edited", true);
    }

    public ApiResponse setState(UUID taskId, TaskSetStateDto taskSetStateDto) {

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        Task task = optionalTask.get();
        task.setState(TaskState.getState(taskSetStateDto.getState()));
        return new ApiResponse("Task state changed", true);

    }

    public ApiResponse delete(UUID taskId) {

        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return new ApiResponse("Task not found", false);
        }
        taskRepository.deleteById(taskId);
        return new ApiResponse("Task deleted", true);
    }
}
