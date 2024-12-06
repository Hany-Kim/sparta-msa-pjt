package com.spring_cloud.eureka.client.order.service;

import com.spring_cloud.eureka.client.order.dto.OrderListResponseDto;
import com.spring_cloud.eureka.client.order.dto.ProductDto;
import com.spring_cloud.eureka.client.order.dto.ProductIdAndAmountResponseDto;
import com.spring_cloud.eureka.client.order.dto.UserDto;
import com.spring_cloud.eureka.client.order.entity.Order;
import com.spring_cloud.eureka.client.order.entity.OrderProduct;
import com.spring_cloud.eureka.client.order.repository.OrderProductRepository;
import com.spring_cloud.eureka.client.order.repository.OrderRepository;
import com.spring_cloud.eureka.client.order.repository.ProductClient;
import com.spring_cloud.eureka.client.order.repository.UserClinet;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    @Value("${service.jwt.secret-key}")
    private String secretKey;


    private final UserClinet userClinet;

    private final OrderProductRepository orderProductRepository;

    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    @Transactional
    public OrderListResponseDto addOrder(HttpServletRequest request) {
        String token = getToken(request);
        String authorizationHeader = "Bearer " + token;

        // 사용자 영속성 체크
        String username = getUsernameAtToken(token);
        System.out.println("username = " + username);

        UserDto userDto = userClinet.getUser(request, username);
        // 주문 생성
        System.out.println("userDto.getUsername() = " + userDto.getUsername());

        List<OrderProduct> orderProductList = new ArrayList<>();
        Order newOrder = Order.from(userDto.getUsername(), orderProductList);
        Order order = orderRepository.save(newOrder);

        List<ProductIdAndAmountResponseDto> productList = new ArrayList<>();
        for (OrderProduct op : orderProductList) {
            ProductIdAndAmountResponseDto productIdAndAmountResponseDto = ProductIdAndAmountResponseDto.from(op.getProductId(), op.getQuantity());
            productList.add(productIdAndAmountResponseDto);
        }

        // 응답 DTO 작성
        OrderListResponseDto responseDto = new OrderListResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setProducts(productList);

        return responseDto;
    }

    @Transactional
    public OrderListResponseDto addProductToOrder(HttpServletRequest request, Long orderId, Long productId) {
        String token = getToken(request);
        String authorizationHeader = "Bearer " + token;

        // 사용자 영속성 체크
        String username = getUsernameAtToken(token);
        UserDto userDto = userClinet.getUser(request, username);
        // 상품 영속성 체크
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
//        Product product = productClient.getProduct(productId);
        ProductDto productDto = productClient.getProduct(productId);

        // 주문 영속성 체크
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if(!order.getUsername().equals(username)){
            throw new IllegalArgumentException("주문을 생성한 사용자가 아닙니다.");
        }

        // 이미 주문된 상품인지 검증
        Optional<OrderProduct> orderProduct = orderProductRepository.findByUsernameAndProductIdAndOrder(userDto.getUsername(), productDto.getProductId(), order);

        OrderProduct newOrderProduct = new OrderProduct();
        if(orderProduct.isPresent()){
            // 주문된 상품이라면 수량 증가
            orderProduct.get().plusQuantity();
            newOrderProduct = orderProduct.get();
        } else {
            // 최초 주문이면 신규 생성
            newOrderProduct = orderProductRepository.save(new OrderProduct(userDto.getUsername(), productDto.getProductId(), order));
        }

        List<ProductIdAndAmountResponseDto> productList = new ArrayList<>();
        List<OrderProduct> orderProductList = order.getProductIds();
        for (OrderProduct op : orderProductList) {
            ProductIdAndAmountResponseDto productIdAndAmountResponseDto = ProductIdAndAmountResponseDto.from(op.getProductId(), op.getQuantity());
            productList.add(productIdAndAmountResponseDto);
        }

        // 응답 DTO 작성
        OrderListResponseDto responseDto = new OrderListResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setProducts(productList);

        return responseDto;
    }

    public OrderListResponseDto getOrders(Long orderId) {
        // 주문 영속성 체크
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        List<ProductIdAndAmountResponseDto> productList = new ArrayList<>();
        List<OrderProduct> orderProductList = order.getProductIds();
        for (OrderProduct op : orderProductList) {
            ProductIdAndAmountResponseDto productIdAndAmountResponseDto = ProductIdAndAmountResponseDto.from(op.getProductId(), op.getQuantity());
            productList.add(productIdAndAmountResponseDto);
        }

        // 응답 DTO 작성
        OrderListResponseDto responseDto = new OrderListResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setProducts(productList);

        return responseDto;
    }

    private String getUsernameAtToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        String username = (String) claimsJws.getBody().get("username");
        return username;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return token;
    }
}
