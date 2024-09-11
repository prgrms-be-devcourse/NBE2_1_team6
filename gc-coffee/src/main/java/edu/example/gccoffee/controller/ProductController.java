package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/v1/products")
@Controller
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public String productsPage(Model model) {
        model.addAttribute("products", productService.getList());
        return "product/product-list";
    }

    @GetMapping("new-product")
    public String newProductPage() {
        return "product/new-product";
    }

    @PostMapping("/products")
    public String newProduct(ProductDTO productDTO) {
        productService.create(productDTO);
        return "redirect:/products";
    }
}
