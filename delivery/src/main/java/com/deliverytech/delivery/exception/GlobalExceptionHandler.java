package com.deliverytech.delivery.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("code", "VALIDATION_ERROR");
        error.put("timestamp", LocalDateTime.now().toString());

        Map<String, String> details = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.put("details", details);

        body.put("error", error);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("code", "ENTITY_NOT_FOUND");
        error.put("timestamp", LocalDateTime.now().toString());

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("message", ex.getMessage());
        error.put("details", details);

        body.put("error", error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("code", "BUSINESS_ERROR");
        error.put("timestamp", LocalDateTime.now().toString());

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("message", ex.getMessage());
        error.put("details", details);

        body.put("error", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("code", "INTERNAL_ERROR");
        error.put("timestamp", LocalDateTime.now().toString());

        Map<String, Object> details = new LinkedHashMap<>();
        details.put("message", "Ocorreu um erro inesperado");
        error.put("details", details);

        body.put("error", error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
