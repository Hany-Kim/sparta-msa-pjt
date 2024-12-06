package com.spring_cloud.eureka.client.auth.controller;

import com.spring_cloud.eureka.client.auth.dto.UserDto;
import com.spring_cloud.eureka.client.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public UserDto getUser(@RequestHeader("Authorization") String token, @PathVariable String username) {
        return userService.getUser(username);
    }
}
