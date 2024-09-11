package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@Log4j2
public class OrderRestController {
    private final OrderService orderService;

    //주문하기
    @PostMapping("")
    public ResponseEntity<OrderDTO> OrderRegister(@Validated @RequestBody OrderDTO orderDTO) {
        log.info("--- OrderRegister()");
        log.info("--- OrderDTO: " + orderDTO);


        return ResponseEntity.ok(orderService.add(orderDTO));
    }

    //주문자 주문 목록
    @GetMapping("/{email}")
    public ResponseEntity<List<OrderDTO>> GetOrders(@PathVariable String email) {
        log.info("--- GetOrders()");
        log.info("--- email: " + email);


        return ResponseEntity.ok(orderService.read(email));
    }

    //모든 주문 조회
    @GetMapping("")
    public ResponseEntity<List<OrderDTO>> GetAllOrders() {
        log.info("--- GetAllOrders()");


        return ResponseEntity.ok(orderService.readAll());
    }

    //주문 수정하기
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> orderModify(@PathVariable Long orderId,
                                                @Validated @RequestBody OrderDTO orderDTO) {
        log.info("--- OrderUpdate()");
        log.info("--- orderId: " + orderId);
        log.info("--- OrderDTO: " + orderDTO);

        orderDTO.setOrderId(orderId);
        log.info("--- OrderDTO: " + orderDTO);
        return ResponseEntity.ok(orderService.update(orderDTO));
    }

    //주문 삭제하기
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Map<String, String>> orderDelete(@PathVariable Long orderId) {
        log.info("--- OrderDelete()");
        log.info("--- orderId: " + orderId);

        orderService.delete(orderId);

        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
