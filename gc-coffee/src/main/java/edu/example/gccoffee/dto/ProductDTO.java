package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "상품 정보(ProductDTO)", description = "상품에 대한 정보를 담고 있는 객체.")
public class ProductDTO {
    @Schema(description = "상품 번호", example = "1")
    private Long productId;

    @Schema(description = "상품 이름", example = "Columbia Coffee")
    @NotEmpty
    private String productName;

    @Schema(description = "카테고리", example = "COFFEE_BEAN_PACKAGE")
    @NotEmpty
    private String category;

    @Schema(description = "상품 가격", example = "10000")
    @Min(0)
    private int price;

    @Schema(description = "상품 설명", example = "콜롬비아의 맛있는 커피")
    private String description;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.category = product.getCategory().name();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }

    public Product toEntity() {
        return Product.builder().productId(productId).productName(productName).description(description).category(Category.valueOf(category)).price(price).build();
    }
}
