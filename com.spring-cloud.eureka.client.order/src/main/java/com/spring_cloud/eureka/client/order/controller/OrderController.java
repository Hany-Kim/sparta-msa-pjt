package com.spring_cloud.eureka.client.order.controller;

import com.spring_cloud.eureka.client.auth.common.security.UserDetailsImpl;
import com.spring_cloud.eureka.client.order.dto.OrderListResponseDto;
import com.spring_cloud.eureka.client.order.service.OrderService;
import com.spring_cloud.eureka.client.product.dto.ProductIdRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Secured({"ROLE_CUSTOMER"})
    @PostMapping
    public ResponseEntity<OrderListResponseDto> addOrder(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderListResponseDto responseDto = orderService.addOrder(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    @Secured({"ROLE_CUSTOMER"})
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderListResponseDto> addProductToOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable Long orderId,
                                                            @RequestBody ProductIdRequestDto requestDto) {

        OrderListResponseDto responseDto = orderService.addProductToOrder(userDetails.getUsername(), orderId, requestDto.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderListResponseDto> getOrders(@PathVariable Long orderId) {

        OrderListResponseDto responseDto = orderService.getOrders(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
