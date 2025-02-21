package com.salomovs95.event.generator.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salomovs95.event.generator.dto.CreateEventDto;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.exception.EventCreationException;
import com.salomovs95.event.generator.repository.EventRepository;

@Service
public class EventService {
  private final EventRepository eventRepository;

  public EventService(final EventRepository repository) {
    this.eventRepository = repository;
  }

  public EventEntity create(CreateEventDto dto) throws Exception {
    if (dto == null) throw new EventCreationException("Invalid data provided");

    final LocalDate startDate = dto.startDate(), endDate = dto.endDate();
    System.out.println(
      "Date is invalid: " + (
      startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() <=
      endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    ));

    if (startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now()) || startDate.isBefore(endDate)) {
      throw new EventCreationException("Invalid event period");
    }

    String prettyName = dto.title().toLowerCase().replaceAll(" ", "-");
    Optional<EventEntity> eventToBe = eventRepository.findByPrettyName(prettyName);

    if (eventToBe.isPresent()) return eventToBe.get();
    
    EventEntity event = eventRepository.save(new EventEntity(
      null,
      dto.title(),
      prettyName,
      dto.price(),
      dto.location(),
      startDate,
      endDate
    ));

    return event;
  }

  public Optional<EventEntity> findEvent(Integer eventId) {
    return eventRepository.findById(eventId);
  }

  public Optional<EventEntity> findEvent(String prettyName) {
    return eventRepository.findByPrettyName(prettyName);
  }

  public List<EventEntity> findAll() {
    return eventRepository.findAll();
  }
}
