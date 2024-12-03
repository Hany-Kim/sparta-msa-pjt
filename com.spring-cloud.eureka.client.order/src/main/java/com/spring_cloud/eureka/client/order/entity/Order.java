package com.spring_cloud.eureka.client.order.entity;

import com.spring_cloud.eureka.client.auth.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> productIds;

    public static Order from(User user, List<OrderProduct> productIdList) {
        return Order.builder()
            .user(user)
            .productIds(productIdList)
            .build();
    }
}
