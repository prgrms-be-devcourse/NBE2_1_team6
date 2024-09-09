package edu.example.gccoffee.dto;

import edu.example.gccoffee.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListDTO {

    private Long productId;
    private String productName;
    private Category category;
    private int price;
    private String description;
}
