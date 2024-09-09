package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.repository.OrderItemRepository;
import edu.example.gccoffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        for(OrderItem orderItem : orderItems){
            orderItemRepository.save(orderItem);
        }
    }

    public List<OrderItemDTO> getAllItems(String email) {     //조회
       return null;
    }
}
