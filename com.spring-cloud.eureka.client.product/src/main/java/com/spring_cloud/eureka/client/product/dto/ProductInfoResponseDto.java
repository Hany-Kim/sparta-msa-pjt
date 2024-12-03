package com.spring_cloud.eureka.client.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResponseDto {
    private Long productId;
    private String name;
    private int supplyPrice;
}
