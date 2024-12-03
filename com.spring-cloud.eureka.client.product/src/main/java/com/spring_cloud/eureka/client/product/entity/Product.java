package com.spring_cloud.eureka.client.product.entity;

import com.spring_cloud.eureka.client.product.dto.ProductInfoRequestDto;
import com.spring_cloud.eureka.client.product.dto.ProductInfoResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int supplyPrice;

    public static Product from(ProductInfoRequestDto requestDto) {
        return Product.builder()
                .name(requestDto.getName())
                .supplyPrice(requestDto.getSupplyPrice())
                .build();
    }

    public ProductInfoResponseDto toProductInfoResponseDto() {
        return new ProductInfoResponseDto(productId, name, supplyPrice);
    }
}
