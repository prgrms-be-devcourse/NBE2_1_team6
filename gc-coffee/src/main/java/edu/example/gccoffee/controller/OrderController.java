package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.entity.Order;
import edu.example.gccoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@Log4j2
public class OrderController {
    private final OrderService orderService;

    //주문하기
    @PostMapping("")
    public ResponseEntity<OrderDTO> OrderRegister(@Validated @RequestBody OrderDTO orderDTO) {
        log.info("--- OrderRegister()");
        log.info("--- OrderDTO: " + orderDTO);


        return null;
    }
}
