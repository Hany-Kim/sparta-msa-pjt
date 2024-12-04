package com.spring_cloud.eureka.client.product.dto;

import com.spring_cloud.eureka.client.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long productId;
    private String name;
    private int supplyPrice;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .supplyPrice(product.getSupplyPrice())
                .build();
    }
}
