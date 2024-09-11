package com.mballem.demo_park_api.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
@Data
@NoArgsConstructor
@ToString
public class ErrorMsg {

    private String path;
    private String status;
    private int statusCode;
    private String method;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,String> errors;



    public ErrorMsg(HttpStatus status, HttpServletRequest request, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMsg(BindingResult result,HttpStatus status, HttpServletRequest request, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.statusCode = status.value();
        this.status = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    private void addErrors(BindingResult result) {
        this.errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            this.errors.put(error.getField(), error.getDefaultMessage());
        }
    }
}
