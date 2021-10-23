package com.behl.dolores.exception.handler;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverExceptionHandler(Exception exception) {
        log.error("Exception Caught:", exception);
        final var response = new HashMap<String, String>();
        response.put("Message", "Either you or i did something wrong, it's probably you cause you're dumb");
        response.put("exeption", exception.getClass().getTypeName());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }

}