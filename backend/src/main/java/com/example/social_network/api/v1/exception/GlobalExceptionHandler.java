package com.example.social_network.api.v1.exception;

import com.example.social_network.api.v1.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request) {

        String status = String.valueOf(ex.getStatus().value());
        ErrorResponse error = new ErrorResponse(List.of(new ErrorResponse.ErrorResponseAttributes(
            status,
            ex.getStatus().getReasonPhrase(),
            ex.getMessage()
        )));

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>("Invalid request format", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Tu lógica existente para otras excepciones de runtime.
        if ("Database connection failed".equals(ex.getMessage())) {
            return new ResponseEntity<>("Internal Server Error: Database connection failed",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
