package com.apress.controller.secured;

import com.apress.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivateAdminController {
    private final UserService userService;

    @Autowired
    public PrivateAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getManagementPage(Model model) {
        model.addAttribute("userName", userService.getTheUser().getUserName());
        return "private/admin/management-page";
    }


}
