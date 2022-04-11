package com.snw.cloud.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod() {
        return "User Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/departmentServiceFallBack")
    public String departmentServiceFallBackMethod() {
        return "Department Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/authServiceFallBack")
    public String authServiceFallBackMethod() {
        return "Auth Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/postServiceFallBack")
    public String postServiceFallBackMethod() {
        return "Post Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/feedServiceFallBack")
    public String feedServiceFallBackMethod() {
        return "Feed Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/mediaServiceFallBack")
    public String mediaServiceFallBackMethod() {
        return "Media Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/commentServiceFallBack")
    public String commentServiceFallBackMethod() {
        return "Comment Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/likeServiceFallBack")
    public String likeServiceFallBackMethod() {
        return "Like Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/notifications-fallback")
    public String notificationsServiceFallBackMethod() {
        return "Notification Service is taking longer than Expected." +
                " Please try again later";
    }
}