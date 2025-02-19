package com.salomovs95.event.generator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs95.event.generator.dto.CreateUserDto;
import com.salomovs95.event.generator.dto.ErrorMessage;
import com.salomovs95.event.generator.dto.SubscriptionResponse;
import com.salomovs95.event.generator.exception.EventNotFoundException;
import com.salomovs95.event.generator.exception.SubscriptionAlreadyExistsException;
import com.salomovs95.event.generator.service.SubscriptionService;

@RestController @RequestMapping("/subscriptions")
public class SubscriptionController {
  private final SubscriptionService subscriptionService;
  private final Logger logg;

  public SubscriptionController(final SubscriptionService service) {
    this.subscriptionService = service;
    this.logg = LoggerFactory.getLogger(SubscriptionController.class);
  }

  @PostMapping({"/{prettyName}", "/{prettyName}/{referralId}"})
  public ResponseEntity<?> getHostUrl(@PathVariable String prettyName, @PathVariable Integer referralId, @RequestBody CreateUserDto body) {
    if (referralId == null) {
      referralId = -1;
    }

    try {
      SubscriptionResponse response = subscriptionService.subscribe(
        prettyName,
        referralId,
        body.username(),
        body.email());
      return ResponseEntity.ok().body(response);
    } catch(EventNotFoundException e) {
      logg.error(String.format("Error while subscribing to event %s", prettyName) + e.getStackTrace());
      return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
    } catch(SubscriptionAlreadyExistsException e) {
      logg.error(String.format("Error while subscribing to event %s", prettyName) + e.getStackTrace());
      return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
    } catch (Exception e) {
      logg.error(String.format("Error while subscribing to event %s", prettyName) + e.getStackTrace());
      return ResponseEntity.status(500).body(new ErrorMessage(e.getMessage()));
    }
  }
}
