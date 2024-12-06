package com.spring_cloud.eureka.client.product.controller;

import com.spring_cloud.eureka.client.product.dto.ProductDto;
import com.spring_cloud.eureka.client.product.dto.ProductIdResponseDto;
import com.spring_cloud.eureka.client.product.dto.ProductInfoRequestDto;
import com.spring_cloud.eureka.client.product.dto.ProductListResponseDto;
import com.spring_cloud.eureka.client.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @Value("${server.port}") // 애플리케이션이 실행 중인 포트를 주입받습니다.
    private String serverPort;

    private final ProductService productService;

    //@Secured({"ROLE_OWNER", "ROLE_MANAGER", "ROLE_MASTER"})
    @PostMapping
    public ResponseEntity<ProductIdResponseDto> createProduct(
            @RequestBody ProductInfoRequestDto requestDto
    ) {
        System.out.println("createProduct 호출=====");
        ProductIdResponseDto responseDto = productService.createProduct(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ProductListResponseDto> getProductList() {

        ProductListResponseDto responseDto = productService.getProductList();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable("productId") Long productId) {

        ProductDto productDto = productService.getProduct(productId);

        return productDto;
    }
}