package com.spring_cloud.eureka.client.order.repository;

import com.spring_cloud.eureka.client.auth.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserClinet {

    @GetMapping("/user/{username}")
    User getUser(@PathVariable("username") String username);
}
