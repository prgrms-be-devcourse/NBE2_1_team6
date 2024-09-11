package edu.example.gccoffee.controller.advice;

import edu.example.gccoffee.exception.OrderTaskException;
import edu.example.gccoffee.exception.ProductTaskException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class APIControllerAdvice {
    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<?> handleProductTaskException(ProductTaskException e) {
        log.info("--- ProductTaskException");
        log.info("--- e.getClass().getName() : " + e.getClass().getName());
        log.info("--- e.getMessage() : " + e.getMessage());

        Map<String, String> errMap = Map.of("error", e.getMessage());


        return ResponseEntity.status(e.getStatusCode()).body(errMap);
    }

    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<?> handleOrderTaskException(OrderTaskException e) {
        log.info("--- OrderTaskException");
        log.info("--- e.getClass().getName() : " + e.getClass().getName());
        log.info("--- e.getMessage() : " + e.getMessage());

        Map<String, String> errMap = Map.of("error", e.getMessage());


        return ResponseEntity.status(e.getStatusCode()).body(errMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("error", "Type Mismatched.");

        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }
}
