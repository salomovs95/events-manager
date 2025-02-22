package com.salomovs95.event.generator.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs95.event.generator.dto.CreateEventDto;
import com.salomovs95.event.generator.dto.EventTopFiveItem;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.service.EventService;

@RestController @RequestMapping("/events")
public class EventController {
  private final EventService eventService;

  public EventController(final EventService service) {
    this.eventService = service;
  }

  @PostMapping("") @Operation(summary="Event Creation", description="Creates a new event.")
  public ResponseEntity<?> createEvent(@RequestBody CreateEventDto dto) throws Exception {
      EventEntity event = eventService.create(dto);
      return ResponseEntity.status(201).body(event);
  }

  @GetMapping("") @Operation(summary="Events Retrieval", description="Retrieves a list of available events.")
  public ResponseEntity<List<EventTopFiveItem>> listTopEvents() {
    return ResponseEntity.status(200).body(eventService.listTopFiveEvents());
  }

  @GetMapping("/{prettyName}") @Operation(summary="Event Retrieval", description="Retrieves information about a given event.")
  public ResponseEntity<EventEntity> findEventByPrettyName(@PathVariable String prettyName) throws Exception {
    EventEntity event = eventService.findEvent(prettyName);
    return ResponseEntity.status(200).body(event);
  }
}
