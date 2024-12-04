package com.spring_cloud.eureka.client.auth.controller;

import com.spring_cloud.eureka.client.auth.entity.User;
import com.spring_cloud.eureka.client.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        User user = userService.getUser(username);
        return user;
    }
}
