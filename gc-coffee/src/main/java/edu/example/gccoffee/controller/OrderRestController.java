package edu.example.gccoffee.controller;

import edu.example.gccoffee.dto.OrderDTO;
import edu.example.gccoffee.service.OrderService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
@Log4j2
@Tag(name = "주문 API 컨트롤러", description = "주문 API를 제공하는 컨트롤러입니다.")
public class OrderRestController {
    private final OrderService orderService;

    //주문하기
    @PostMapping("")
    @Operation(summary = "주문 등록", description = "입력받은 데이터를 통해 주문을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문을 성공적으로 등록했습니다."),
            @ApiResponse(responseCode = "404", description = "주문등록이 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(example = "{\"error\": \"주문 등록에 실패하였습니다.\"}")))
    })
    public ResponseEntity<OrderDTO> OrderRegister(@Validated @RequestBody OrderDTO orderDTO) {
        log.info("--- OrderRegister()");
        log.info("--- OrderDTO: " + orderDTO);


        return ResponseEntity.ok(orderService.add(orderDTO));
    }

    //주문자 주문 목록
    @GetMapping("/{email}")
    @Operation(summary = "고객 주문 목록 조회", description = "고객의 email을 입력받아 주문자의 주문 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문을 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "주문 조회를 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"등록된 주문이 존재하지 않습니다.\"}")))
    })
    public ResponseEntity<List<OrderDTO>> GetOrders(@PathVariable String email) {
        log.info("--- GetOrders()");
        log.info("--- email: " + email);


        return ResponseEntity.ok(orderService.read(email));
    }

    //모든 주문 조회
    @GetMapping("")
    @Operation(summary = "주문 목록 조회", description = "존재하는 모든 주문 목록을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문을 성공적으로 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "주문 조회를 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"등록된 주문이 존재하지 않습니다.\"}")))
    })
    public ResponseEntity<List<OrderDTO>> GetAllOrders() {
        log.info("--- GetAllOrders()");


        return ResponseEntity.ok(orderService.readAll());
    }

    //주문 수정하기
    @PutMapping("/{orderId}")
    @Operation(summary = "주문 수정", description = "고객의 주문 ID를 입력받아 주문 및 주문 상품을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문을 성공적으로 수정했습니다."),
            @ApiResponse(responseCode = "404", description = "주문 수정을 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"주문 상태가 배송중이어서 수정이 불가능합니다.\"}")))
    })
    public ResponseEntity<OrderDTO> orderModify(@PathVariable Long orderId,
                                                @Validated @RequestBody OrderDTO orderDTO) {
        log.info("--- OrderUpdate()");
        log.info("--- orderId: " + orderId);
        log.info("--- OrderDTO: " + orderDTO);

        orderDTO.setOrderId(orderId);
        log.info("--- OrderDTO: " + orderDTO);
        return ResponseEntity.ok(orderService.update(orderDTO));
    }

    //주문 삭제하기
    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 삭제", description = "고객의 주문 ID를 입력받아 주문을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "주문을 성공적으로 삭제했습니다."),
            @ApiResponse(responseCode = "404", description = "주문 삭제를 실패하였습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"error\": \"주문 삭제를 실패하였습니다.\"}")))
    })
    public ResponseEntity<Map<String, String>> orderDelete(@PathVariable Long orderId) {
        log.info("--- OrderDelete()");
        log.info("--- orderId: " + orderId);

        orderService.delete(orderId);

        return ResponseEntity.ok(Map.of("result", "success"));
    }
}
