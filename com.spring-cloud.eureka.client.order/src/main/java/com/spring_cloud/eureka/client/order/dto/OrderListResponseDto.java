package com.spring_cloud.eureka.client.order.dto;

import com.spring_cloud.eureka.client.product.dto.ProductIdAndAmountResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListResponseDto {
    private Long orderId;
    private List<ProductIdAndAmountResponseDto> products;

}
