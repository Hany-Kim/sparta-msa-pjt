package com.spring_cloud.eureka.client.product.repository;

import com.spring_cloud.eureka.client.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
