package com.apress.controller.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {
    @ExceptionHandler(Exception.class)
    public String handleThrowable() {
        return "redirect:/error";
    }

    @RequestMapping("/error")
    public String getErrorPage() {
        return "public/error/error-page";
    }
}
