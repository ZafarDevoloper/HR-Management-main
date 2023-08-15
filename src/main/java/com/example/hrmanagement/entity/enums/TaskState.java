package com.example.hrmanagement.entity.enums;

public enum TaskState {

    NEW,
    IN_PROGRESS,
    FINISHED;

    public static TaskState getState(String stateString) {
        return switch (stateString) {
            case "in_progress" -> IN_PROGRESS;
            case "finished" -> FINISHED;
            case "new" -> NEW;
            default -> null;
        };
    }

}
