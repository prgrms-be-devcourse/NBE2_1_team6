package edu.example.gccoffee.controller.advice;

import edu.example.gccoffee.exception.ProductTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIControllerAdvice {
    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<?> handleProductTaskException(ProductTaskException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(e.getStatusCode()).body("입력값이 유효하지 않습니다.");
    }
}
