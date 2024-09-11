package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.PageRequestDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Log4j2
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getList() {
        return ResponseEntity.ok(productService.getList());
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ProductDTO>> getPage(@Validated PageRequestDTO pageRequestDTO) {
        return ResponseEntity.ok(productService.getPage(pageRequestDTO));
    }
}
