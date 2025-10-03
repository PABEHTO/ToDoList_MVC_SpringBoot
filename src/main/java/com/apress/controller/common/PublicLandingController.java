package com.apress.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PublicLandingController {

    @GetMapping()
    public String getLandingPage() {
        System.out.println("HEREEEEE!!!!");
        return "public/landing-page";
    }
}
