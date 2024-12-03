package com.spring_cloud.eureka.client.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductListResponseDto {
    private List<ProductInfoResponseDto> productList;

}
