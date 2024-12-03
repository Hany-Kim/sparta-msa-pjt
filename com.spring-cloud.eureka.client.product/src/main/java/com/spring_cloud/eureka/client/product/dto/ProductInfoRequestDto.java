package com.spring_cloud.eureka.client.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfoRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private int supplyPrice;
}
