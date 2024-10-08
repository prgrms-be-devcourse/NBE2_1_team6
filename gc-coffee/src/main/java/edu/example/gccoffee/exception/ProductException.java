package edu.example.gccoffee.exception;

import org.springframework.http.HttpStatus;

public enum ProductException {
    PRODUCT_NOT_FOUND("조건과 일치하는 상품이 없습니다.", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_REGISTERED("상품 등록에 실패했습니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_MODIFIED("상품 수정에 실패했습니다.", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_REMOVED("상품 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
    private ProductTaskException productTaskException;

    ProductException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ProductTaskException getProductTaskException() {
        return new ProductTaskException(this.message, this.status.value());
    }

    public ProductTaskException get(){
        return productTaskException;
    }
}
