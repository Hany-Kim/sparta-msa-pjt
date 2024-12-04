package com.spring_cloud.eureka.client.order.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int quantity;

//    @ManyToOne
//    @JoinColumn(name = "username")
//    private User user;
    @Column(nullable = false)
    private String username; // User의 식별자

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
    @Column(nullable = false)
    private Long productId; // User의 식별자

    public OrderProduct(String username, Long productId, Order order) {
        this.username = username;
        this.productId = productId;
        this.order = order;
        this.quantity = 1;
    }

    public void plusQuantity() {
        this.quantity++;
    }
}
