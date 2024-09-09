package edu.example.gccoffee.exception;

public class ProductTaskException extends RuntimeException {

    private final int statusCode;

    public ProductTaskException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
