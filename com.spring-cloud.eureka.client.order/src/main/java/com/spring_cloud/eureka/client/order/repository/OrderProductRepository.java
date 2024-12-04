package com.spring_cloud.eureka.client.order.repository;

import com.spring_cloud.eureka.client.order.entity.Order;
import com.spring_cloud.eureka.client.order.entity.OrderProduct;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    Optional<OrderProduct> findByUsernameAndProductIdAndOrder(String username, Long productId, Order order);
}
