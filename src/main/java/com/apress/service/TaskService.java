package com.apress.service;

import com.apress.dao.TaskDao;
import com.apress.entity.State;
import com.apress.entity.Task;
import com.apress.entity.dto.TaskContainerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {
    private final TaskDao taskDao;

    @Autowired
    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TaskContainerDto findAllTasks(String filterParam) {
        //return taskDao.findAllTasks();
        List<Task> tasks = taskDao.findAllTasks();
        int doneTasksQuantity = (int)tasks.stream().filter(t -> t.getState().equals(State.DONE)).count();
        int activeTasksQuantity = (int)tasks.stream().filter(t -> t.getState().equals(State.ACTIVE)).count();

        if (filterParam == null) {
            return new TaskContainerDto(tasks, doneTasksQuantity, activeTasksQuantity);
        }
        List<String> existFilters = Arrays.stream(State.values()).map(Enum::name).collect(Collectors.toList());

        if (existFilters.contains(filterParam.toUpperCase())) {
            List<Task> filteredTasks =  tasks
                    .stream()
                    .filter(t -> t.getState() == State.valueOf(filterParam.toUpperCase()))
                    .collect(Collectors.toList());
            return new TaskContainerDto(filteredTasks, doneTasksQuantity, activeTasksQuantity);
        }

        return new TaskContainerDto(tasks, doneTasksQuantity, activeTasksQuantity);
    }

    public void saveTask(String name) {
        if (name != null && !name.isBlank()) {
            taskDao.saveTask(new Task(name, State.ACTIVE));
        }
    }

    public void finishTask(int id) {
        taskDao.finishTask(id);
    }

    public void deleteTask(int id) {
        taskDao.deleteTask(id);
    }
}
