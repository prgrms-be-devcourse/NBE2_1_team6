package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.PageRequestDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "제품 API 컨트롤러", description = "제품 관련 API를 제공하는 컨트롤러입니다.")
public class ProductRestController {
    private final ProductService productService;

    @Operation(summary = "제품 목록 조회 (페이지네이션 적용)", description = "페이징을 적용하여 제품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 목록을 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "제품이 존재하지 않거나 조회하는데 실패했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"조건과 일치하는 상품이 없습니다.\"}"))
            )
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호"),
            @Parameter(name = "size", description = "페이지 크기")
    })
    @GetMapping("/pages")
    public ResponseEntity<Page<ProductDTO>> getPage(@Validated PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(productService.getPage(pageRequestDTO));
    }

    @Operation(summary = "제품 목록 조회", description = "전체 제품 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제품 목록을 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "제품이 존재하지 않거나 조회하는데 실패했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"조건과 일치하는 상품이 없습니다.\"}"))
            )
    })
    @GetMapping("/lists")
    public ResponseEntity<List<ProductDTO>> getList() {
        return ResponseEntity.ok(productService.getList());
    }
}
