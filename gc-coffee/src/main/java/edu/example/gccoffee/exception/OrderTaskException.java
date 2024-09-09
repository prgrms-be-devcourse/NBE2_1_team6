package edu.example.gccoffee.exception;

public class OrderTaskException extends RuntimeException {

    private final int statusCode;

    public OrderTaskException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
