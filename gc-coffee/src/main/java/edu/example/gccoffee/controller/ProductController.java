package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.PageRequestDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductService productService;

    @GetMapping("/lists")
    public ResponseEntity<List<ProductDTO>> getList() {
        return ResponseEntity.ok(productService.getList());
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ProductDTO>> getPage(@Validated PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(productService.getPage(pageRequestDTO));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@Validated @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> update(@Validated @RequestBody ProductDTO productDTO, @PathVariable Long productId) {
        productDTO.setProductId(productId);
        return ResponseEntity.ok(productService.update(productDTO));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
