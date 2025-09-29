package com.example.est_bootcamp.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/error/403")
    public String error403() {
        return "error/403"; // templates/error/403.html
    }
}
