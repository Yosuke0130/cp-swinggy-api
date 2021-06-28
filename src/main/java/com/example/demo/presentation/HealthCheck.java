package com.example.demo.presentation;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/status")
public class HealthCheck {

    @ResponseBody
    @GetMapping("/")
    public String getStatus() {

        return "Hello World from Swinggy API!";
    }
}
