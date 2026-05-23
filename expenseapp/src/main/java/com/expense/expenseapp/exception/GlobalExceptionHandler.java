package com.expense.expenseapp.exception;

import com.expense.expenseapp.response.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.
        ControllerAdvice;

import org.springframework.web.bind.annotation.
        ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>>
    handleRuntimeException(
            RuntimeException ex) {

        ApiResponse<String> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }
}