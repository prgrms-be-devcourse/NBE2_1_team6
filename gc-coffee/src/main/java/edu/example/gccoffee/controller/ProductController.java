package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
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

    @GetMapping("product/create")
    public String newProductPage() {
        return "product/new-product";
    }

    @PostMapping("/products")
    public String createProduct(ProductDTO productDTO) {
        productService.create(productDTO);
        return "redirect:/products";
    }

    @GetMapping("product/update/{id}")
    public String modifyProductPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.read(id));
        return "product/modify-product";
    }

    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.update(productDTO);
        return "redirect:/products";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
