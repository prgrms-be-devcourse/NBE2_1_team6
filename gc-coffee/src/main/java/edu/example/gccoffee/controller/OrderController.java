package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.service.OrderItemService;
import edu.example.gccoffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping()
    public String ordersPage(Model model) {
        List<OrderDTO> orderDTOs = orderService.readAll();
        model.addAttribute("orders", orderDTOs);
        return "orders/order-list";
    }

    @GetMapping("/detail/{id}")
    public String ordersDetailsPage(@PathVariable(name = "id") Long orderId, Model model) {
        List<OrderItemDTO> orderItemDTOs = orderItemService.readAll(orderId);
        model.addAttribute("orderItems", orderItemDTOs);
        return "orders/order-item-list";
    }
}
