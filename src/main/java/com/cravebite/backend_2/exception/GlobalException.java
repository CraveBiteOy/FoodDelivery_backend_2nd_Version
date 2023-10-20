package com.cravebite.backend_2.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cravebite.backend_2.models.response.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    /**
     * Handles validation errors from incoming requests.
     * Returns a map of field errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessages(errors.toString()); 

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles custom APIException.
     * Returns a structured ErrorResponse.
     */
    @ExceptionHandler(CraveBiteGlobalExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleAPIException(CraveBiteGlobalExceptionHandler ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getStatus().value());
        // errorResponse.setMessage(ex.getMessage());
        errorResponse.setMessages(ex.getMessage());

        return new ResponseEntity<>(errorResponse, ex.getStatus());

    }

    /**
     * Handles all other uncaught exceptions.
     * Returns a generic ErrorResponse.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // errorResponse.setMessage(ex.getMessage());
        errorResponse.setMessages(ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}