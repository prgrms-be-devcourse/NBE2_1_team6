package edu.example.gccoffee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.OrderItem;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.exception.OrderException;
import edu.example.gccoffee.exception.OrderTaskException;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.exception.ProductTaskException;
import edu.example.gccoffee.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.awaitility.Awaitility.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@WebAppConfiguration
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OrderService orderService;

    private final String BASE_URI = "api/v1/orders";

    @BeforeEach
    void setupBeforeEachMethod() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("주문 등록 테스트 (성공)")
    void test_OrderRegister_When_Success() throws Exception {
        OrderDTO requestDTO = OrderDTO.builder()
                .orderId(1L)
                .email("email@email.com")
                .address("address")
                .postCode(123456)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            OrderItem orderItem = OrderItem.builder()
                    .orderItemId((long) i)
                    .category(Category.COFFEE_BEAN_PACKAGE)
                    .quantity(10)
                    .price(1000)
                    .build();

            orderItems.add(orderItem);
        });

        String jsonRequestBody = new ObjectMapper().writeValueAsString(requestDTO);

        String resultDTO

        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk()).
    }

    @Test
    @DisplayName("주문 단일조회 테스트 (성공)")
    void test_GetOrders_When_Success() throws Exception {
        OrderDTO resultDTO = OrderDTO.builder()
                .orderId(1L)
                .email("email@email.com")
                .address("address")
                .postCode(123456)
                .orderStatus(OrderStatus.DELIVERING)
                .build();

        when(orderService.read(anyString())).thenReturn(resultDTO);

        mockMvc.perform(get(BASE_URI)
                        .param("email", "user1@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("1"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.address").value("address"))
                .andExpect(jsonPath("$.postCode").value(123456))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.DELIVERING.name()));
    }

    @Test
    @DisplayName("주문 단일조회 테스트 (실패)")
    void test_GetOrders_When_Failure() throws Exception {
        OrderTaskException orderTaskException = OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        when(orderService.read(anyString())).thenThrow(orderTaskException);

        mockMvc.perform(get(BASE_URI)
                        .param("email", "user1@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("주문 전체조회 테스트 (성공)")
    void test_GetAllOrders_When_Success() throws Exception {
        mockMvc.perform(get(BASE_URI))
                .andExpect()
    }


    @Test
    @DisplayName("주문 수정 테스트 (성공)")
    void test_OrderModify_When_Success() throws Exception {
        OrderDTO requestDTO = OrderDTO.builder()
                .orderId(1L)
                .email("email@email.com")
                .address("address")
                .postCode(123456)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            OrderItem orderItem = OrderItem.builder()
                    .orderItemId((long) i)
                    .category(Category.COFFEE_BEAN_PACKAGE)
                    .quantity(10)
                    .price(1000)
                    .build();

            orderItems.add(orderItem);
        });

        OrderDTO resultDTO = OrderDTO.builder()
                .orderId(1L)
                .email("email@email.com")
                .address("address")
                .postCode(123456)
                .orderStatus(OrderStatus.DELIVERING)
                .orderItem(orderItems)
                .build();

        when(orderService.update(any(OrderDTO.class))).thenReturn(resultDTO);

        String jsonRequestBody = new ObjectMapper().writeValueAsString(requestDTO);

        mockMvc.perform(put(BASE_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("1"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.postCode").value(123456))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.DELIVERING.name()))
                .andExpect(jsonPath("$.orderItems[0].orderItemId").value("1"))
                .andExpect(jsonPath("$.orderItems[0].category").value(Category.COFFEE_BEAN_PACKAGE.name()))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(10))
                .andExpect(jsonPath("$.orderItems[0].price").value(1000))
                .andExpect(jsonPath("$.orderItems[1].orderItemId").value("2"))
                .andExpect(jsonPath("$.orderItems[1].category").value(Category.COFFEE_BEAN_PACKAGE.name()))
                .andExpect(jsonPath("$.orderItems[1].quantity").value(10))
                .andExpect(jsonPath("$.orderItems[1].price").value(1000));
    }

    @Test
    @DisplayName("주문 수정 테스트 (실패)")
    void test_OrderModify_When_Failure() throws Exception {
        OrderTaskException orderTaskException = OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        when(orderService.update(any(OrderDTO.class))).thenThrow(orderTaskException);

        OrderDTO requestDTO = new OrderDTO();

        String jsonRequestBody = new ObjectMapper().writeValueAsString(requestDTO);

        mockMvc.perform(put(BASE_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(orderTaskException.getMessage()));
    }

    @Test
    @DisplayName("주문 삭제 테스트")
    void test_OrderDelete_When_Success() throws Exception {
        doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete(BASE_URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"));
    }

    private OrderDTO createOrderDTO() {
        return OrderDTO.builder().
    }
}
