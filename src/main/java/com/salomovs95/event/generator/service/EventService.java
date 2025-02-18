package com.salomovs95.event.generator.service;

import org.springframework.stereotype.Service;

import com.salomovs95.event.generator.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class EventService {
  private EventRepository eventRepository;
}
