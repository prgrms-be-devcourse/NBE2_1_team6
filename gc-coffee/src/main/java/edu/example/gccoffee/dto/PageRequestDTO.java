package edu.example.gccoffee.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    @Builder.Default
    @Min(1)
    private int page = 1;
    @Builder.Default
    @Min(5)
    private int size = 5;

    public Pageable toPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
