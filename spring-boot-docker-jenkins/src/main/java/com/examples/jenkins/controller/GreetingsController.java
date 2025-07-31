package com.examples.jenkins.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class GreetingsController {

    @GetMapping("/")
    public String welcome(HttpServletRequest request) {
        return "Hello there, Welcome to Spring Boot with Jenkins CI/CD Demo! " +
               "You are currently at: " + getFullURL(request);
    }

    private String getFullURL(HttpServletRequest request) {
        return request.getRequestURL().toString();
    }

    @GetMapping("/greetings")
    public String greetings(@RequestParam(value = "name", defaultValue = "Guest") String name) {
        return String.format("Hello, %s! Welcome to Spring Boot with Jenkins CI/CD Demo!", name);
    }
    
    @GetMapping("/health")
    public String healthCheck() {
        return "Application is up and running!";
    }
}
