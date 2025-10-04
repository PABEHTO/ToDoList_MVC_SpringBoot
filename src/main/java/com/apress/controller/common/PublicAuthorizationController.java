package com.apress.controller.common;

import com.apress.entity.User;
import com.apress.entity.UserRole;
import com.apress.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicAuthorizationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PublicAuthorizationController(UserService userService,
                                         PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("isLoginFailed", true);
            //return "public/error/login-fail-page";
        }
        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration")
    public String createUserAccount(@RequestParam("userName") String userName,
                                    @RequestParam("login") String login,
                                    @RequestParam("password")String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userService.save(new User(userName, login, encodedPassword, UserRole.USER));
        return "redirect:/login";
    }

}
