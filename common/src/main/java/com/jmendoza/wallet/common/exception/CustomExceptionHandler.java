package com.jmendoza.wallet.common.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger loggerException = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(String.valueOf(HttpStatus.NOT_FOUND))
                .payload(ex.getMessage())
                .build();
        loggerException.error(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity globalExceptionHandler(GlobalException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .payload(ex.getMessage())
                .build();
        loggerException.error(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ParameterNotFoundException.class})
    public ResponseEntity parameterNotFoundExceptionHandler(ParameterNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST))
                .payload(ex.getMessage())
                .build();
        loggerException.error(ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
