package com.salomovs95.event.generator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.salomovs95.event.generator.dto.ErrorMessage;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
  public ResponseEntity<ErrorMessage> handleException(Exception e) {
    final ErrorMessage err = new ErrorMessage(e.getMessage(), e.getClass().getName());
    final Logger logg = LoggerFactory.getLogger(ExceptionHandler.class);

    logg.error(e.getMessage());

    if (e instanceof EventNotFoundException ||
        e instanceof SubscriptionNotFoundException) {
      return ResponseEntity.status(404).body(err);
    }

    if (e instanceof EventCreationException ||
        e instanceof SubscriptionAlreadyExistsException) {
      return ResponseEntity.status(400).body(err);
    }

    return ResponseEntity.status(500).body(err);
  }
}
