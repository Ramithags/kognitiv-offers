package com.kognitive.offers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContentExceptionController {

    @ExceptionHandler(value = ContentNotFoundException.class)
    public ResponseEntity<Object> exception(ContentNotFoundException exception) {
        return new ResponseEntity<>("Name not found", HttpStatus.NOT_FOUND);
    }
}
