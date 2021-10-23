package com.behl.dolores.exception.handler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.behl.dolores.exception.PossibleSqlInjectionAttackException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    @ExceptionHandler(PossibleSqlInjectionAttackException.class)
    public ResponseEntity<?> possibleSqlInjectionAttackExceptionHandler(
            final PossibleSqlInjectionAttackException exception) {
        log.error("Exception Caught:", exception);
        final var response = new HashMap<String, String>();
        response.put("Message", "Trying to attack me with SQL Injection?, Fuck you!");
        response.put("exeption", exception.getClass().getSimpleName());
        response.put("timestamp",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS").format(LocalDateTime.now(ZoneId.of("+00:00"))));
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
    }

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> serverExceptionHandler(final Exception exception) {
        log.error("Exception Caught:", exception);
        final var response = new HashMap<String, String>();
        response.put("Message", "Either you or i did something wrong, it's probably you cause you're dumb");
        response.put("exeption", exception.getClass().getSimpleName());
        response.put("timestamp",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS").format(LocalDateTime.now(ZoneId.of("+00:00"))));
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
    }

}