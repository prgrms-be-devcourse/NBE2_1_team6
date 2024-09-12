package edu.example.gccoffee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    @BeforeAll
    void setupBeforeAll() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("제품 조회 페이지 테스트")
    void test_ProductsPage() throws Exception {
        List<ProductDTO> mockResult = mock(List.class);

        when(productService.getList()).thenReturn(mockResult);

        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/product-list"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", mockResult));
    }

    @Test
    @DisplayName("제품 등록 페이지 테스트")
    void test_NewProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/new-product"));
    }

    @Test
    @DisplayName("제품 등록 처리 테스트")
    void test_CreateProduct() throws Exception {
        String jsonRequestBody = objectMapper.writeValueAsString(ProductDTO.builder()
                .productName("Columbia Coffee")
                .category("COFFEE_BEAN_PACKAGE")
                .price(10000)
                .description("콜롬비아의 맛있는 커피")
                .build());

        when(productService.create(any(ProductDTO.class))).thenReturn(mock(ProductDTO.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));
    }

    @Test
    @DisplayName("제품 수정 페이지 테스트")
    void test_ModifyProductPage() throws Exception {
        ProductDTO mockResult = mock(ProductDTO.class);

        when(productService.read(anyLong())).thenReturn(mockResult);

        mockMvc.perform(get("/product/update/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/modify-product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", mockResult));
    }

    @Test
    @DisplayName("제품 수정 처리 테스트")
    void test_UpdateProduct() throws Exception {
        ProductDTO mockResult = mock(ProductDTO.class);

        when(productService.read(anyLong())).thenReturn(mockResult);

        mockMvc.perform(get("/product/update/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/modify-product"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", mockResult));
    }

    @Test
    @DisplayName("제품 삭제 처리 테스트")
    void test_DeleteProduct() throws Exception {
        doNothing().when(productService).delete(anyLong());

        mockMvc.perform(get("/product/delete/{id}", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"));
    }
}
