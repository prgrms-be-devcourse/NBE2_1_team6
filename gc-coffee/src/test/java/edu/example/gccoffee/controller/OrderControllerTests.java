package edu.example.gccoffee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.example.gccoffee.controller.advice.APIControllerAdvice;
import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.dto.OrderItemDTO;
import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.OrderStatus;
import edu.example.gccoffee.exception.OrderException;
import edu.example.gccoffee.exception.OrderTaskException;
import edu.example.gccoffee.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private final String BASE_URI = "/api/v1/orders";

    @BeforeEach
    void setupBeforeEachMethod() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OrderController(orderService))
                .setControllerAdvice(new APIControllerAdvice())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("주문 등록 테스트 (성공)")
    void test_OrderRegister_When_Success() throws Exception {
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .orderItemId((long) i)
                    .category(Category.COFFEE_BEAN_PACKAGE.name())
                    .quantity(10)
                    .price(1000)
                    .build();

            orderItemDTOs.add(orderItemDTO);
        });

        OrderDTO orderDTO = OrderDTO.builder()
                .orderId(1L)
                .email("email@email.com")
                .address("address")
                .postCode(123456)
                .orderItem(orderItemDTOs)
                .orderStatus(OrderStatus.DELIVERING)
                .build();

        when(orderService.add(orderDTO)).thenReturn(orderDTO);

        String jsonRequestBody = new ObjectMapper().writeValueAsString(orderDTO);

        mockMvc.perform(post(BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("1"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.postCode").value(123456))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.DELIVERING.name()))
                .andExpect(jsonPath("$.orderItem[0].orderItemId").value("1"))
                .andExpect(jsonPath("$.orderItem[0].category").value(Category.COFFEE_BEAN_PACKAGE.name()))
                .andExpect(jsonPath("$.orderItem[0].quantity").value(10))
                .andExpect(jsonPath("$.orderItem[0].price").value(1000))
                .andExpect(jsonPath("$.orderItem[1].orderItemId").value("2"))
                .andExpect(jsonPath("$.orderItem[1].category").value(Category.COFFEE_BEAN_PACKAGE.name()))
                .andExpect(jsonPath("$.orderItem[1].quantity").value(10))
                .andExpect(jsonPath("$.orderItem[1].price").value(1000));
    }

    @Test
    @DisplayName("주문 조회 테스트 (성공)")
    void test_GetOrders_When_Success() throws Exception {
        List<OrderDTO> resultDTOs = new ArrayList<>();

        IntStream.rangeClosed(1, 2).forEach(i -> {
            OrderDTO resultDTO = OrderDTO.builder()
                    .orderId((long) i)
                    .email("user1@gmail.com")
                    .address("address")
                    .postCode(123456)
                    .orderStatus(OrderStatus.DELIVERING)
                    .build();
            resultDTOs.add(resultDTO);
        });

        when(orderService.read(anyString())).thenReturn(resultDTOs);

        mockMvc.perform(get(BASE_URI + "/{email}", "user1@email.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(1))  // 숫자로 비교
                .andExpect(jsonPath("$[0].email").value("user1@gmail.com"))  // email 맞춤
                .andExpect(jsonPath("$[0].address").value("address"))
                .andExpect(jsonPath("$[0].postCode").value(123456))
                .andExpect(jsonPath("$[0].orderStatus").value(OrderStatus.DELIVERING.name()))
                .andExpect(jsonPath("$[1].orderId").value(2))  // 두 번째 아이템 검증
                .andExpect(jsonPath("$[1].email").value("user1@gmail.com"))
                .andExpect(jsonPath("$[1].address").value("address"))
                .andExpect(jsonPath("$[1].postCode").value(123456))
                .andExpect(jsonPath("$[1].orderStatus").value(OrderStatus.DELIVERING.name()));
    }

    @Test
    @DisplayName("주문 조회 테스트 (실패)")
    void test_GetOrders_When_Failure() throws Exception {
        OrderTaskException orderTaskException = OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        when(orderService.read(anyString())).thenThrow(orderTaskException);

        mockMvc.perform(get(BASE_URI + "/{email}", "user1@email.com"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(orderTaskException.getMessage()));
    }

    @Test
    @DisplayName("주문 전체조회 테스트 (성공)")
    void test_GetAllOrders_When_Success() throws Exception {
        List<OrderDTO> resultDTOs = new ArrayList<>();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            OrderDTO resultDTO = OrderDTO.builder()
                    .orderId((long) i)
                    .email("email@email.com")
                    .address("address")
                    .postCode(123456)
                    .orderStatus(OrderStatus.DELIVERING)
                    .orderItem(null)
                    .build();
            resultDTOs.add(resultDTO);
        });

        when(orderService.readAll()).thenReturn(resultDTOs);

        mockMvc.perform(get(BASE_URI))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value("1"))
                .andExpect(jsonPath("$[0].email").value("email@email.com"))
                .andExpect(jsonPath("$[0].postCode").value(123456))
                .andExpect(jsonPath("$[0].orderStatus").value(OrderStatus.DELIVERING.name()))
                .andExpect(jsonPath("$[1].orderId").value("2"))
                .andExpect(jsonPath("$[1].email").value("email@email.com"))
                .andExpect(jsonPath("$[1].postCode").value(123456))
                .andExpect(jsonPath("$[1].orderStatus").value(OrderStatus.DELIVERING.name()));
    }

    @Test
    @DisplayName("주문 전체조회 테스트 (실패)")
    void test_GetAllOrders_When_Failure() throws Exception {
        OrderTaskException orderTaskException = OrderException.ORDER_NOT_FOUND.getOrderTaskException();
        when(orderService.readAll()).thenThrow(orderTaskException);

        mockMvc.perform(get(BASE_URI))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(orderTaskException.getMessage()));
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

        List<OrderItemDTO> orderItemsDtos = new ArrayList<>();
        IntStream.rangeClosed(1, 2).forEach(i -> {
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .orderItemId((long) i)
                    .quantity(10)
                    .price(1000)
                    .category(Category.COFFEE_BEAN_PACKAGE.name())
                    .orderId((long) i)
                    .productId((long) i)
                    .build();
            orderItemsDtos.add(orderItemDTO);
        });

        OrderDTO resultDTO = OrderDTO.builder()
                .orderId(1L)
                .email("email@email.com")
                .address("address")
                .postCode(123456)
                .orderStatus(OrderStatus.DELIVERING)
                .orderItem(orderItemsDtos)
                .build();

        when(orderService.update(any(OrderDTO.class))).thenReturn(resultDTO);

        String jsonRequestBody = new ObjectMapper().writeValueAsString(requestDTO);

        mockMvc.perform(put(BASE_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("1"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.postCode").value(123456))
                .andExpect(jsonPath("$.orderStatus").value(OrderStatus.DELIVERING.name()))
                .andExpect(jsonPath("$.orderItem[0].orderItemId").value("1"))
                .andExpect(jsonPath("$.orderItem[0].category").value(Category.COFFEE_BEAN_PACKAGE.name()))
                .andExpect(jsonPath("$.orderItem[0].quantity").value(10))
                .andExpect(jsonPath("$.orderItem[0].price").value(1000))
                .andExpect(jsonPath("$.orderItem[0].orderId").value("1"))
                .andExpect(jsonPath("$.orderItem[0].productId").value("1"))
                .andExpect(jsonPath("$.orderItem[1].orderItemId").value("2"))
                .andExpect(jsonPath("$.orderItem[1].category").value(Category.COFFEE_BEAN_PACKAGE.name()))
                .andExpect(jsonPath("$.orderItem[1].quantity").value(10))
                .andExpect(jsonPath("$.orderItem[1].price").value(1000))
                .andExpect(jsonPath("$.orderItem[1].orderId").value("2"))
                .andExpect(jsonPath("$.orderItem[1].productId").value("2"));
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
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(orderTaskException.getMessage()));
    }

    @Test
    @DisplayName("주문 삭제 테스트 (성공)")
    void test_OrderDelete_When_Success() throws Exception {
        doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete(BASE_URI + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"));
    }
}
