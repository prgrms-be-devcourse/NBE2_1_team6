package edu.example.gccoffee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(title = "페이지 요청 정보(PageRequestDTO)", description = "페이지 요청 정보를 담고 있는 객체.")
public class PageRequestDTO {
    @Schema(description = "페이지 번호", example = "1")
    @Builder.Default
    @Min(1)
    private int page = 1;

    @Schema(description = "페이지 크기", example = "5")
    @Builder.Default
    @Min(5)
    private int size = 5;

    public Pageable toPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
