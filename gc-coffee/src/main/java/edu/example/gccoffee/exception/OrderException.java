package edu.example.gccoffee.exception;

import org.springframework.http.HttpStatus;

public enum OrderException {
    ORDER_NOT_FOUND("사용자의 주문이 없습니다.", HttpStatus.NOT_FOUND),
    ORDER_NOT_REGISTERED("주문이 등록되지 않았습니다.", HttpStatus.BAD_REQUEST),
    ORDER_NOT_MODIFIED("주문이 수정되지 않았습니다.", HttpStatus.BAD_REQUEST),
    ORDERITEM_NOT_MODIFIED("주문 목록이 수정되지 않았습니다.", HttpStatus.BAD_REQUEST),
    ORDER_NOT_MODIFIED_DELIVERING("주문배송중이어서 수정이 불가능합니다.", HttpStatus.BAD_REQUEST),
    ORDER_NOT_REMOVED("주문이 삭제되지 않았습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    OrderException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public OrderTaskException getOrderTaskException() {
        return new OrderTaskException(this.message,this.status.value());
    }
}
