package com.spring_cloud.eureka.client.product.service;

import com.spring_cloud.eureka.client.product.dto.ProductIdResponseDto;
import com.spring_cloud.eureka.client.product.dto.ProductInfoRequestDto;
import com.spring_cloud.eureka.client.product.dto.ProductListResponseDto;
import com.spring_cloud.eureka.client.product.entity.Product;
import com.spring_cloud.eureka.client.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductIdResponseDto createProduct(ProductInfoRequestDto requestDto) {

        Product product = Product.from(requestDto);
        productRepository.save(product);

        return new ProductIdResponseDto(product.getProductId());
    }

    public ProductListResponseDto getProductList() {

        ProductListResponseDto responseDto = new ProductListResponseDto();

        List<Product> productList = productRepository.findAll();

        responseDto.setProductList(productList.stream().map(Product::toProductInfoResponseDto).toList());

        return responseDto;

    }
}
