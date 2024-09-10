package edu.example.gccoffee.service;


import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.*;
import edu.example.gccoffee.exception.OrderException;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.repository.OrderItemRepository;
import edu.example.gccoffee.repository.OrderRepository;
import edu.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;

    public OrderDTO add(OrderDTO orderDTO){   //등록
        Order savedOrder = orderRepository.save(orderDTO.toEntity());
        List<OrderItemDTO> orderItems = orderDTO.getOrderItem();
        for (OrderItemDTO ord : orderItems) {
            Product product = Product.builder().productId(ord.getProductId()).build();
            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(product)
                    .price(ord.getPrice())
                    .quantity(ord.getQuantity())
                    .category(Category.valueOf(ord.getCategory()))
                    .build();

            // OrderItem 저장
            orderItemRepository.save(orderItem);
        }
        return orderDTO;
    }

    public List<OrderDTO> read(String email) {     //조회
        List<Order> order = orderRepository.findByEmail(email);
        if (!order.isEmpty()) {
           List<OrderDTO> orderDTO = new ArrayList<>();

           for (Order od : order) {
               orderDTO.add(new OrderDTO(od));
           }

           return orderDTO;
        } else {
            throw OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        }
    }

    public List<OrderDTO> readAll() {     //조회
        List<Order> order = orderRepository.findAll();
        if (!order.isEmpty()) {
            List<OrderDTO> orderDTO = new ArrayList<>();

            for (Order od : order) {
                orderDTO.add(new OrderDTO(od));
            }

            return orderDTO;
        } else {
            throw OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        }
    }

    public OrderDTO update(OrderDTO orderDTO){
        Optional<Order> foundOrder = orderRepository.findById(orderDTO.getOrderId());   //수정하려는 상품을 데이터베이스에서 조회해서
        Order order = foundOrder.orElseThrow(ProductException.PRODUCT_NOT_FOUND::get);
        List <OrderItemDTO> orderItems = orderDTO.getOrderItem();


        order.changeEmail(orderDTO.getEmail());
        order.changeAddress(orderDTO.getAddress());
        order.changePostCode(orderDTO.getPostCode());

        try{
             orderItemService.modify(orderItems, orderDTO.getOrderId());
        } catch (Exception e){
             log.error("--- " + e.getMessage());
             throw OrderException.ORDERITEM_NOT_MODIFIED.getOrderTaskException();
        }
        return new OrderDTO(order); //변경된 상품을 반환

    }

    public void delete(Long orderId){
        if(orderRepository.findById(orderId).isPresent()){
            try {
                orderRepository.deleteById(orderId);
            } catch (Exception e){
                log.error("--- " + e.getMessage());
                throw OrderException.ORDER_NOT_REMOVED.getOrderTaskException();
            }
        } else{
            throw OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        }
    }

    @Scheduled(cron = "0 0 14 * * ?")
    public void processOrder() {
        List<Order> orders = orderRepository.findAllByOrderStatus(OrderStatus.NOT_DELIVERY);

        if (orders.isEmpty()) {
            return;
        }

        int ordersSize = orders.size();
        orders.forEach(order -> order.changeOrderStatus(OrderStatus.DELIVERING));

        orderRepository.saveAll(orders);
    }
}
