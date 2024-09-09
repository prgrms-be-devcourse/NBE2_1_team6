package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.repository.OrderItemRepository;
import edu.example.gccoffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    public void add(OrderDTO orderDTO){   //등록
        orderRepository.save(orderDTO.toEntity());
        List<OrderItem> orderItems = orderDTO.getOrderItem();
        for (OrderItem orderItem : orderItems) {
            orderItemRepository.save(orderItem);
        }
    }

    public OrderDTO read(String email) {     //조회
        Optional<Order> order = orderRepository.findByEmail(email);
        if (order.isPresent()) {
            return new OrderDTO(order.get());
        } else {
            return null;
        }
    }

    public OrderDTO update(OrderDTO orderDTO){
        Optional<Order> foundOrder = orderRepository.findById(orderDTO.getOrderId());   //수정하려는 상품을 데이터베이스에서 조회해서
        Order order = foundOrder.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);

//        try {
            order.changeEmail(order.getEmail());
            order.changeAddress(order.getAddress());
            order.changePostCode(order.getPostCode());
            order.changeEmail(order.getEmail());
            order.changeEmail(order.getEmail());
            order.changeEmail(order.getEmail());

//            product.clearImages();        //기존 이미지 삭제
//            List<String> images = productDTO.getImages();//새 이미지 목록을 가져와서
//            if (images != null && !images.isEmpty()) {
//                images.forEach(product::addImage);   // 추가
//            }
//
//            return new ProductDTO(product); //변경된 상품을 반환
//        } catch(Exception e) {
//            log.error("--- " + e.getMessage());
//            throw ProductException.NOT_MODIFIED.get();
//        }
            return null;
    }
}
