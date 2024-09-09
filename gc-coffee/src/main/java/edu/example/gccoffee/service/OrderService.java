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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<CartItem> cartItemList = cartItemRepository.getCartItems(customer).orElse(null);

        List<CartItemDTO> itemDTOList = new ArrayList<>();
        if(cartItemList.isEmpty()) { //DB의 장바구니 목록이 비어 있으면
            return  itemDTOList;     //비어 있는 itemDTOList 반환
        }

        cartItemList.forEach(cartItem -> { //비어 있지 않으면
            //엔티티 >>> DTO 변환하여 itemDTOList 저장하여 반환
            itemDTOList.add(CartItemDTO.builder()
                    .itemNo( cartItem.getItemNo())
                    .pno( cartItem.getProduct().getPno())
                    .pname( cartItem.getProduct().getPname())
                    .price( cartItem.getProduct().getPrice())
                    .quantity( cartItem.getQuantity())
                    .image( cartItem.getProduct().getImages()
                            .first().getFilename())
                    .build());
        });
        return itemDTOList;
    }
}
