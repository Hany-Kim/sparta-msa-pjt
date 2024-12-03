package com.spring_cloud.eureka.client.order.repository;

import com.spring_cloud.eureka.client.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
