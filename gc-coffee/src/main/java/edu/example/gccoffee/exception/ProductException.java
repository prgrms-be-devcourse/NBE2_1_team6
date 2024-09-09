package edu.example.gccoffee.exception;

import org.springframework.http.HttpStatus;

public enum ProductException {

    PRODUCT_NOT_FOUND("Product with the given ID was not found", HttpStatus.NOT_FOUND),
    PRODUCT_NAME_NOT_VALID("Product name is invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_CATEGORY_NOT_VALID("Product category is invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_NOT_VALID("Product price must be greater than zero", HttpStatus.BAD_REQUEST),
    PRODUCT_DESCRIPTION_NOT_VALID("Product description is invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_REGISTERED("Product could not be registered", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_MODIFIED("Product could not be modified", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_REMOVED("Product could not be removed", HttpStatus.BAD_REQUEST);

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
