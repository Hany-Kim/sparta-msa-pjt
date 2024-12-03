package com.spring_cloud.eureka.client.product.controller;

import com.spring_cloud.eureka.client.product.dto.ProductIdResponseDto;
import com.spring_cloud.eureka.client.product.dto.ProductInfoRequestDto;
import com.spring_cloud.eureka.client.product.dto.ProductListResponseDto;
import com.spring_cloud.eureka.client.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PostMapping
    public ResponseEntity<ProductIdResponseDto> createProduct(
            @RequestBody ProductInfoRequestDto requestDto
    ) {

        ProductIdResponseDto responseDto = productService.createProduct(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ProductListResponseDto> getProductList() {

        ProductListResponseDto responseDto = productService.getProductList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}