package com.apress.controller.secured;

import com.apress.entity.State;
import com.apress.entity.dto.TaskContainerDto;
import com.apress.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/account")
public class PrivateAccountController {
    private final TaskService taskService;

    @Autowired
    public PrivateAccountController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getMainPage(Model model,@RequestParam(name="login", required = false) String login, @RequestParam(name = "filterParam", required = false) String filterParam) {
        TaskContainerDto container = taskService.findAllTasks(filterParam, login);
        model.addAttribute("tasks", container.getTasks());
        model.addAttribute("numberOfDoneTasks", container.getDoneTasksQuantity());
        model.addAttribute("numberOfActiveTasks", container.getActiveTasksQuantity());
        return "private/main-account-page";
    }

   @PostMapping("/add-task")
    public String addTask(@RequestParam String name) {
        taskService.saveTask(name);
        return "redirect:/account";
    }

    @PostMapping("/finish-task")
    public String finishTask(@RequestParam int id, @RequestParam(name="filterParam", required = false) String filterParam) {
        taskService.updateTaskState(id, State.DONE);
        return "redirect:/account" + (filterParam != null || filterParam.isBlank() ? "?filterParam=" +filterParam : "");
    }

    @PostMapping("/delete-task")
    public String deleteTask(@RequestParam int id, @RequestParam(name="filterParam", required = false) String filterParam) {
        taskService.deleteTask(id);
        return "redirect:/account" + (filterParam != null || filterParam.isBlank() ? "?filterParam=" +filterParam : "");
    }
}
