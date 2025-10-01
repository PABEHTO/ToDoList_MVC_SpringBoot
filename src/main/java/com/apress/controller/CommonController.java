package com.apress.controller;

import com.apress.entity.dto.TaskContainerDto;
import com.apress.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommonController {
    private final TaskService taskService;

    @Autowired
    public CommonController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getMainPage(Model model, @RequestParam(name = "filterParam", required = false) String filterParam) {
        TaskContainerDto container = taskService.findAllTasks(filterParam);
        model.addAttribute("tasks", container.getTasks());
        model.addAttribute("numberOfDoneTasks", container.getDoneTasksQuantity());
        model.addAttribute("numberOfActiveTasks", container.getActiveTasksQuantity());
        return "main-page";
    }

    @RequestMapping(value = "/add-task", method = RequestMethod.POST)
    public String addTask(@RequestParam String name) {
        taskService.saveTask(name);
        return "redirect:/home";
    }

    @RequestMapping("/")
    public String goToHome() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/finish-task", method = RequestMethod.POST)
    public String finishTask(@RequestParam int id, @RequestParam(name="filterParam", required = false) String filterParam) {
        taskService.finishTask(id);
        return "redirect:/home" + (filterParam != null || filterParam.isBlank() ? "?filterParam=" +filterParam : "");
    }

    @RequestMapping(value="/delete-task", method = RequestMethod.POST)
    public String deleteTask(@RequestParam int id, @RequestParam(name="filterParam", required = false) String filterParam) {
        taskService.deleteTask(id);
        return "redirect:/home" + (filterParam != null || filterParam.isBlank() ? "?filterParam=" +filterParam : "");
    }
}
