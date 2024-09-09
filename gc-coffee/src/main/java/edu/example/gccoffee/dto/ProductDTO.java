package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    @NotEmpty
    private String productName;
    @NotEmpty
    private Category category;
    @Min(0)
    private int price;
    private String description;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public Product toEntity() {
        return Product.builder().productId(productId).productName(productName).description(description).category(category).price(price).build();
    }
}
