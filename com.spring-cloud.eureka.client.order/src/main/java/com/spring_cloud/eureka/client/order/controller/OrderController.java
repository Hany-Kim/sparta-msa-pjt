package com.spring_cloud.eureka.client.order.controller;

import com.spring_cloud.eureka.client.order.dto.OrderListResponseDto;
import com.spring_cloud.eureka.client.order.dto.ProductIdRequestDto;
import com.spring_cloud.eureka.client.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Secured({"ROLE_CUSTOMER"})
    @PostMapping
    public ResponseEntity<OrderListResponseDto> addOrder(HttpServletRequest request) {
        String username = (String) request.getAttribute("username"); // JWT에서 추출
        OrderListResponseDto responseDto = orderService.addOrder(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Secured({"ROLE_CUSTOMER"})
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderListResponseDto> addProductToOrder(HttpServletRequest request,
                                                            @PathVariable Long orderId,
                                                            @RequestBody ProductIdRequestDto requestDto) {

        String username = (String) request.getAttribute("username"); // JWT에서 추출
        OrderListResponseDto responseDto = orderService.addProductToOrder(username, orderId, requestDto.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderListResponseDto> getOrders(@PathVariable Long orderId) {

        OrderListResponseDto responseDto = orderService.getOrders(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
