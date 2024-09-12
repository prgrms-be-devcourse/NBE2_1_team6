package edu.example.gccoffee.controller;

import edu.example.gccoffee.controller.advice.APIControllerAdvice;
import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.service.OrderItemService;
import edu.example.gccoffee.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderItemService orderItemService;

    private final String BASE_URI = "/orders";

    @BeforeAll
    void setupBeforeAll() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderController(orderService, orderItemService))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("주문 조회 페이지 테스트")
    public void test_OrdersPage() throws Exception {
        List<OrderDTO> mockResult = mock(List.class);

        when(orderService.readAll()).thenReturn(mockResult);

        mockMvc.perform(get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("orders/order-list"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("orders", mockResult));
    }

    @Test
    @DisplayName("주문 목록 조회 페이지 테스트")
    public void test_OrdersDetailsPage() throws Exception {
        List<OrderItemDTO> mockResult = mock(List.class);

        when(orderItemService.readAll(anyLong())).thenReturn(mockResult);

        mockMvc.perform(get(BASE_URI + "/detail/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("orders/order-item-list"))
                .andExpect(model().attributeExists("orderItems"))
                .andExpect(model().attribute("orderItems", mockResult));
    }
}
