package com.SigortaKampanyaApi.SigortaKampanyaApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(KategoriException.class)
    public ResponseEntity<ApiError> handleKampanyaUpdateException(KategoriException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(KampanyaUpdateException.class)
    public ResponseEntity<ApiError> handleKampanyaUpdateException(KampanyaUpdateException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(KampanyaBulunamadiException.class)
    public ResponseEntity<ApiError> handleKampanyaBulunamadiException(KampanyaBulunamadiException ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex){
        List<String> errors = new ArrayList<>();
        errors.add("Runtime Exception occurred: " + ex.getMessage());
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex){
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), errors);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
