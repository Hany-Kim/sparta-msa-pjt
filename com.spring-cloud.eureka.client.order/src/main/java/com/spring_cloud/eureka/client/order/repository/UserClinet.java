package com.spring_cloud.eureka.client.order.repository;

import com.spring_cloud.eureka.client.order.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="auth-service")
public interface UserClinet {

    @GetMapping("/user/{username}")
    UserDto getUser(@RequestHeader("Authorization") HttpServletRequest authorization, @PathVariable("username") String username);
}
