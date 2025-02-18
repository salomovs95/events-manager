package com.salomovs95.event.generator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salomovs95.event.generator.dto.CreateEventDto;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.repository.EventRepository;

@Service
public class EventService {
  private final EventRepository eventRepository;

  public EventService(final EventRepository repository) {
    this.eventRepository = repository;
  }

  public EventEntity create(CreateEventDto dto) throws Exception {
    if (dto == null) throw new Exception("Invalid data provided");
    if (dto.startDate().isAfter(dto.endDate())) throw new Exception("Invalid event period");
    if (dto.startTime().isAfter(dto.endTime())) throw new Exception("Invalid event duration");

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

}
