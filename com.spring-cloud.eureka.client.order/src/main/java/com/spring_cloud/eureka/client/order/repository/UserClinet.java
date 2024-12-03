package com.spring_cloud.eureka.client.order.repository;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="user-service")
public interface UserClinet {
}
