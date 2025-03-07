package com.salomovs95.event.generator.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

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
import com.salomovs95.event.generator.exception.EventCreationException;
import com.salomovs95.event.generator.exception.EventNotFoundException;
import com.salomovs95.event.generator.service.EventService;

@RestController @RequestMapping("/events") @Tags({
  @Tag(name="Events Handling")
})
public class EventController {
  private final EventService eventService;

  public EventController(final EventService service) {
    this.eventService = service;
  }

  @PostMapping("")
  @Operation(summary="Event Creation", description="Creates a new event.")
  @ApiResponses({
    @ApiResponse(
      responseCode="201",
      description="Event Successfully Created",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"id\":1,\"title\":\"NLW Connect\",\"prettyName\":\"nlw-connnect\",\"price\":0,\"location\":\"Home office\",\"startDate\":\"2025-02-17\",\"endDate\":\"2025-02-20\"}"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="400",
      description="Event Not Created : Bad Request",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Invalid data provided\",\"cause\":\"EventCreationException\"}"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="500",
      description="Event Not Created : Internal Error",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Can't create event\",\"cause\":\"EventCreationException\"}"
          )
        )
      }
    )
  })
  public ResponseEntity<?> createEvent(
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
      description="An event like object",
      content=@Content(
        schema=@Schema(
          type="application/json",
          implementation=CreateEventDto.class,
          example="{\"title\":\"NLW Connect\",\"prettyName\":\"nlw-connnect\",\"price\":0,\"location\":\"Home office\",\"startDate\":\"2025-02-17\",\"endDate\":\"2025-02-20\"}"
        )
      )
    )
    @RequestBody CreateEventDto dto
  ) throws EventCreationException {
      EventEntity event = eventService.create(dto);
      return ResponseEntity.status(201).body(event);
  }

  @GetMapping("")
  @Operation(summary="Events Retrieval", description="Retrieves a list of available events.")
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      description="A list of events",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="[{\"id\":1,\"title\":\"NLW Connect\",\"prettyName\":\"nlw-connnect\",\"price\":0,\"location\":\"Home office\",\"startDate\":\"2025-02-17\",\"endDate\":\"2025-02-20\"}]"
          )
        )
      }
    ),
  })
  public ResponseEntity<List<EventTopFiveItem>> listTopEvents() {
    return ResponseEntity.status(200).body(eventService.listTopFiveEvents());
  }

  @GetMapping("/{prettyName}")
  @Operation(summary="Event Retrieval", description="Retrieves information about a given event.")
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      description="A signle event details, if available",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"id\":1,\"title\":\"NLW Connect\",\"prettyName\":\"nlw-connnect\",\"price\":0,\"location\":\"Home office\",\"startDate\":\"2025-02-17\",\"endDate\":\"2025-02-20\"}"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="404",
      description="Event Not Found",
      content={
        @Content(
          schema=@Schema(
            type="application/json",
            implementation=ResponseEntity.class,
            example="{\"error\":\"Event not found\",\"cause\":\"EventNotFoundException\"}"
          )
        )
      }
    )
  })
  public ResponseEntity<EventEntity> findEventByPrettyName(@PathVariable String prettyName) throws EventNotFoundException {
    EventEntity event = eventService.findEvent(prettyName);
    return ResponseEntity.status(200).body(event);
  }
}
