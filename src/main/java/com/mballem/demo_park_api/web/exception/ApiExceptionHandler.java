package com.mballem.demo_park_api.web.exception;

import com.mballem.demo_park_api.DBExcep.DBUsername;
import com.mballem.demo_park_api.DBExcep.EntityNotFoundExcep;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DBUsername.class)
    public ResponseEntity<ErrorMsg> handleValidationE(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMsg(HttpStatus.CONFLICT,request, ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundExcep.class)
    public ResponseEntity<ErrorMsg> handleValidationEntity(RuntimeException ex, HttpServletRequest request) {
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMsg(HttpStatus.NOT_FOUND,request, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMsg> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();
        ErrorMsg errorMsg = new ErrorMsg(result, HttpStatus.UNPROCESSABLE_ENTITY, request, "Invalid fields");
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMsg);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsg> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorMsg errorMsg = new ErrorMsg(HttpStatus.INTERNAL_SERVER_ERROR, request, "An unexpected error occurred");
        log.error("Api Error - ", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMsg);
    }


}