package com.salomovs95.event.generator.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs95.event.generator.dto.CreateEventDto;
import com.salomovs95.event.generator.dto.ErrorMessage;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.exception.EventCreationException;
import com.salomovs95.event.generator.service.EventService;

@RestController @RequestMapping("/events")
public class EventController {
  private final EventService eventService;
  private Logger logg;

  public EventController(final EventService service) {
    this.eventService = service;
    this.logg = LoggerFactory.getLogger(EventController.class);
  }

  @PostMapping("") @Operation(summary="Event Creation", description="Creates a new event.")
  public ResponseEntity<?> createEvent(@RequestBody CreateEventDto dto) {
    try {
      EventEntity event = eventService.create(dto);
      return ResponseEntity.status(201).body(event);
    } catch(EventCreationException e) {
      logg.error("Cannot create event. " + e.getStackTrace());
      return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
    } catch(Exception e) {
      logg.error("Cannot create event. " + e.getStackTrace());
      return ResponseEntity.status(500).body(new ErrorMessage(e.getMessage()));
    }
  }

  @GetMapping("") @Operation(summary="Events Retrieval", description="Retrieves a list of available events.")
  public ResponseEntity<List<EventEntity>> listAllEvents() {
    return ResponseEntity.status(200).body(eventService.findAll());
  }

  @GetMapping("/{prettyName}") @Operation(summary="Event Retrieval", description="Retrieves information about a given event.")
  public ResponseEntity<EventEntity> findEventByPrettyName(@PathVariable String prettyName) {
    logg.info(String.format("Searching an event with params <prettyName: %s>", prettyName));
    EventEntity event = eventService.findEvent(prettyName).orElse(null);
    return ResponseEntity.status(event==null?404:200).body(event);
  }
}
