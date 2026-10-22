package com.enterprise.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String redirectToHealth() {
        return "redirect:/actuator/health";
    }
}
