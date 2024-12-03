package com.spring_cloud.eureka.client.order.repository;

import com.example.demo.model.user.entity.User;
import com.spring_cloud.eureka.client.order.entity.Order;
import com.spring_cloud.eureka.client.order.entity.OrderProduct;
import com.spring_cloud.eureka.client.product.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    Optional<OrderProduct> findByUserAndProductAndOrder(User user, Product product, Order order);
}
