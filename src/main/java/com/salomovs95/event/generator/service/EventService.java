package com.salomovs95.event.generator.service;

import java.time.LocalDate;
import java.time.LocalTime;
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

    validateEventPeriod(dto.startDate(), dto.endDate(), dto.startTime(), dto.endTime());

    String prettyName = dto.title().toLowerCase().replaceAll(" ", "-");
    Optional<EventEntity> eventToBe = eventRepository.findByPrettyName(prettyName);

    if (eventToBe.isPresent()) return eventToBe.get();
    
    EventEntity event = eventRepository.save(new EventEntity(
      null,
      dto.title(),
      prettyName,
      dto.price(),
      dto.location(),
      dto.startDate(),
      dto.endDate(),
      dto.startTime(),
      dto.endTime()
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

  private void validateEventPeriod(LocalDate startDate,
                                   LocalDate endDate,
                                   LocalTime startTime,
                                   LocalTime endTime) throws Exception {
    final LocalDate TODAY = LocalDate.now();
    final LocalTime NOW = LocalTime.now();

    if (startDate.isBefore(TODAY) || endDate.isBefore(TODAY)) throw new EventCreationException("Past events are not allowed");
    if (startTime.isBefore(NOW) || endTime.isBefore(NOW)) throw new EventCreationException("Past events are not allowed");
    if (startDate.isAfter(endDate)) throw new EventCreationException("Invalid event period");
    if (startTime.isAfter(endTime)) throw new EventCreationException("Invalid event duration");
  }
}
