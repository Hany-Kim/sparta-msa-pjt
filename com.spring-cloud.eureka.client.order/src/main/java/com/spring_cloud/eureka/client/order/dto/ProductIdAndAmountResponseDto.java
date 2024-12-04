package com.spring_cloud.eureka.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductIdAndAmountResponseDto {
    private Long productId;
    private int amount;

    public static ProductIdAndAmountResponseDto from(Long productId, int quantity) {
        return ProductIdAndAmountResponseDto.builder()
                .productId(productId)
                .amount(quantity)
                .build();
    }
}
