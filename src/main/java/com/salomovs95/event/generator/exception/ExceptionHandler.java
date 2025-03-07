package com.salomovs95.event.generator.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.salomovs95.event.generator.dto.ErrorMessage;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandler {
  private final String BASE_PKG="com.salomovs95.event.generator.exception.";
  private Logger logg;
  
  public ExceptionHandler() {
    this.logg = LoggerFactory.getLogger(ExceptionHandler.class);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleException(EventNotFoundException e) {
    logg.error("EVENT NOT FOUND # " + e.getMessage());
    return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage(), e.getClass().getName().replace(BASE_PKG,"")));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(SubscriptionNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleException(SubscriptionNotFoundException e) {
    logg.error("SUBSCRIPTION NOT FOUND # " + e.getMessage());
    return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage(), e.getClass().getName().replace(BASE_PKG,"")));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(EventCreationException.class)
  public ResponseEntity<ErrorMessage> handleException(EventCreationException e) {
    logg.error("CANT CREATE EVENT # " + e.getMessage());
    return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage(), e.getClass().getName().replace(BASE_PKG,"")));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(SubscriptionAlreadyExistsException.class)
  public ResponseEntity<ErrorMessage> handleException(SubscriptionAlreadyExistsException e) {
    logg.error("BAD REQUEST # " + e.getMessage());
    return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage(), e.getClass().getName().replace(BASE_PKG,"")));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorMessage> handleException(ConstraintViolationException e) {
    logg.error("BAD REQUEST # " + e.getMessage());
    return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage(), e.getClass().getName().replace(BASE_PKG,"")));
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> handleException(Exception e) {
    logg.error("INTERNAL ERROR # ", e.getMessage());
    return ResponseEntity.status(500).body(new ErrorMessage(e.getMessage(), e.getClass().getName().replace(BASE_PKG,"")));
  }
}
