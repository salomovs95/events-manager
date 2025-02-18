package com.salomovs95.event.generator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs95.event.generator.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/events") @RequiredArgsConstructor
public class EventController {
  private EventService eventService;
}
