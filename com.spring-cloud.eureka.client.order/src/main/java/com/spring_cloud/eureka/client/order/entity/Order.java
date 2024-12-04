package com.spring_cloud.eureka.client.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    @ManyToOne
//    private User user;
    @Column(nullable = false)
    private String username; // User의 식별자

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> productIds;

    public static Order from(String username, List<OrderProduct> productIdList) {
        return Order.builder()
            .username(username)
            .productIds(productIdList)
            .build();
    }
}
