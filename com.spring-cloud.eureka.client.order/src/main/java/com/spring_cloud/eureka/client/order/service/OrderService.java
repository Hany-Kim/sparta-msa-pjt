package com.spring_cloud.eureka.client.order.service;

import com.spring_cloud.eureka.client.auth.entity.User;
import com.spring_cloud.eureka.client.order.dto.OrderListResponseDto;
import com.spring_cloud.eureka.client.order.entity.Order;
import com.spring_cloud.eureka.client.order.entity.OrderProduct;
import com.spring_cloud.eureka.client.order.repository.OrderProductRepository;
import com.spring_cloud.eureka.client.order.repository.OrderRepository;
import com.spring_cloud.eureka.client.order.repository.ProductClient;
import com.spring_cloud.eureka.client.order.repository.UserClinet;
import com.spring_cloud.eureka.client.product.dto.ProductIdAndAmountResponseDto;
import com.spring_cloud.eureka.client.product.entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    //private final UserRepository userRepository;
    private final UserClinet userClinet;

    private final OrderProductRepository orderProductRepository;

    //private final ProductRepository productRepository;
    private final ProductClient productClient;

    private final OrderRepository orderRepository;

    @Transactional
    public OrderListResponseDto addOrder(String username) {

        // 사용자 영속성 체크
//        User user = userRepository.findById(username)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User user = userClinet.getUser(username);

        // 주문 생성

        List<OrderProduct> orderProductList = new ArrayList<>();
        Order newOrder = Order.from(user, orderProductList);
        Order order = orderRepository.save(newOrder);

        List<ProductIdAndAmountResponseDto> productList = new ArrayList<>();
        for (OrderProduct op : orderProductList) {
            ProductIdAndAmountResponseDto productIdAndAmountResponseDto = ProductIdAndAmountResponseDto.from(op.getProduct().getProductId(), op.getQuantity());
            productList.add(productIdAndAmountResponseDto);
        }

        // 응답 DTO 작성
        OrderListResponseDto responseDto = new OrderListResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setProducts(productList);

        return responseDto;
    }

    @Transactional
    public OrderListResponseDto addProductToOrder(String username, Long orderId, Long productId) {

        // 사용자 영속성 체크
//        User user = userRepository.findById(username)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        User user = userClinet.getUser(username);

        // 상품 영속성 체크
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Product product = productClient.getProduct(productId);

        // 주문 영속성 체크
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if(!order.getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("주문을 생성한 사용자가 아닙니다.");
        }

        // 이미 주문된 상품인지 검증
        Optional<OrderProduct> orderProduct = orderProductRepository.findByUserAndProductAndOrder(user, product, order);

        OrderProduct newOrderProduct = new OrderProduct();
        if(orderProduct.isPresent()){
            // 주문된 상품이라면 수량 증가
            orderProduct.get().plusQuantity();
            newOrderProduct = orderProduct.get();
        } else {
            // 최초 주문이면 신규 생성
            newOrderProduct = orderProductRepository.save(new OrderProduct(user, product, order));
        }

        List<ProductIdAndAmountResponseDto> productList = new ArrayList<>();
        List<OrderProduct> orderProductList = order.getProductIds();
        for (OrderProduct op : orderProductList) {
            ProductIdAndAmountResponseDto productIdAndAmountResponseDto = ProductIdAndAmountResponseDto.from(op.getProduct().getProductId(), op.getQuantity());
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
            ProductIdAndAmountResponseDto productIdAndAmountResponseDto = ProductIdAndAmountResponseDto.from(op.getProduct().getProductId(), op.getQuantity());
            productList.add(productIdAndAmountResponseDto);
        }

        // 응답 DTO 작성
        OrderListResponseDto responseDto = new OrderListResponseDto();
        responseDto.setOrderId(order.getOrderId());
        responseDto.setProducts(productList);

        return responseDto;
    }
}
