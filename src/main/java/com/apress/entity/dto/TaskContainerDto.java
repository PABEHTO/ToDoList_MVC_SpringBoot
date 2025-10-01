package com.apress.entity.dto;

import com.apress.entity.Task;

import java.util.List;

public class TaskContainerDto {
    private final List<Task> tasks;
    private final int doneTasksQuantity;
    private final int activeTasksQuantity;

    public TaskContainerDto(List<Task> tasks, int doneTasksQuantity, int activeTasksQuantity) {
        this.tasks = tasks;
        this.doneTasksQuantity = doneTasksQuantity;
        this.activeTasksQuantity = activeTasksQuantity;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int getDoneTasksQuantity() {
        return doneTasksQuantity;
    }

    public int getActiveTasksQuantity() {
        return activeTasksQuantity;
    }

}
