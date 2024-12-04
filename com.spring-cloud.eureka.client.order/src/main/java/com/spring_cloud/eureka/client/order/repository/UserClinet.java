package com.spring_cloud.eureka.client.order.repository;

import com.spring_cloud.eureka.client.order.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserClinet {

    @GetMapping("/user/{username}")
    UserDto getUser(@PathVariable("username") String username);
}
