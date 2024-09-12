package edu.example.gccoffee.controller;

import edu.example.gccoffee.controller.advice.APIControllerAdvice;
import edu.example.gccoffee.dto.PageRequestDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.exception.ProductTaskException;
import edu.example.gccoffee.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductRestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String BASE_URI = "/api/v1/products";
    private List<ProductDTO> results;

    @BeforeAll
    void setupBeforeAll() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductRestController(productService))
                .setControllerAdvice(new APIControllerAdvice())
                .addFilter(new CharacterEncodingFilter("UTF-8"))
                .build();

        results = new ArrayList<>();

        IntStream.rangeClosed(1, 3).forEach(i -> {
            ProductDTO result = ProductDTO.builder()
                    .productId((long) i)
                    .productName("PRODUCT_NAME_" + i)
                    .category(Category.COFFEE_BEAN_PACKAGE.name())
                    .description("PRODUCT_DESC_" + i)
                    .price(i * 1000)
                    .build();

            results.add(result);
        });
    }

    @Test
    @DisplayName("제품 목록 페이징 조회 (성공)")
    void test_GetPage_When_Success() throws Exception {
        PageRequest pageRequest = PageRequest.of(1, 5);

        Page<ProductDTO> pages = new PageImpl<>(results, pageRequest, 5);

        when(productService.getPage(any(PageRequestDTO.class))).thenReturn(pages);

        mockMvc.perform(get(BASE_URI + "/pages")
                .queryParam("page", "1")
                .queryParam("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(generateResultMatchers(pages));
    }

    @Test
    @DisplayName("제품 목록 페이징 조회 (실패)")
    void test_GetPage_When_Failure() throws Exception {
        ProductTaskException exception = ProductException.PRODUCT_NOT_FOUND.getProductTaskException();

        when(productService.getPage(any(PageRequestDTO.class))).thenThrow(exception);

        mockMvc.perform(get(BASE_URI + "/pages")
                .queryParam("page", "1")
                .queryParam("size", "10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value(exception.getMessage()));
    }

    @Test
    @DisplayName("제품 목록 리스트 조회 (성공)")
    void test_GetList_When_Success() throws Exception {
        when(productService.getList()).thenReturn(results);

        mockMvc.perform(get(BASE_URI + "/lists"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpectAll(generateResultMatchers(results));
    }

    private ResultMatcher[] generateResultMatchers(List<ProductDTO> templates) {
        List<ResultMatcher> resultMatchers = new ArrayList<>();

        for (int i = 0; i < templates.size(); i++) {
            ProductDTO template = templates.get(i);
            String prefix = String.format("$[%d].", i);
            resultMatchers.add(jsonPath(prefix + "productId").value(String.valueOf(template.getProductId())));
            resultMatchers.add(jsonPath(prefix + "productName").value(template.getProductName()));
            resultMatchers.add(jsonPath(prefix + "category").value(template.getCategory()));
            resultMatchers.add(jsonPath(prefix + "description").value(template.getDescription()));
            resultMatchers.add(jsonPath(prefix + "price").value(template.getPrice()));
        }

        return resultMatchers.toArray(ResultMatcher[]::new);
    }

    private ResultMatcher[] generateResultMatchers(Page<ProductDTO> pages) {
        List<ResultMatcher> resultMatchers = new ArrayList<>();

        String prefix = "$.";
        resultMatchers.add(jsonPath(prefix + "totalPages").value(pages.getTotalPages()));
        resultMatchers.add(jsonPath(prefix + "totalElements").value(pages.getTotalElements()));
        resultMatchers.add(jsonPath(prefix + "numberOfElements").value(pages.getNumberOfElements()));
        resultMatchers.add(jsonPath(prefix + "first").value(pages.isFirst()));
        resultMatchers.add(jsonPath(prefix + "last").value(pages.isLast()));
        resultMatchers.add(jsonPath(prefix + "empty").value(pages.isEmpty()));

        prefix = "$.pageable.";
        resultMatchers.add(jsonPath(prefix + "pageNumber").value(pages.getNumber()));
        resultMatchers.add(jsonPath(prefix + "pageSize").value(pages.getSize()));

        List<ProductDTO> templates = pages.getContent();
        for (int i = 0; i < templates.size(); i++) {
            ProductDTO template = templates.get(i);
            prefix = String.format("$.content.[%d].", i);
            resultMatchers.add(jsonPath(prefix + "productId").value(String.valueOf(template.getProductId())));
            resultMatchers.add(jsonPath(prefix + "productName").value(template.getProductName()));
            resultMatchers.add(jsonPath(prefix + "category").value(template.getCategory()));
            resultMatchers.add(jsonPath(prefix + "description").value(template.getDescription()));
            resultMatchers.add(jsonPath(prefix + "price").value(template.getPrice()));
        }

        return resultMatchers.toArray(ResultMatcher[]::new);
    }
}
