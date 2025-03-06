package com.salomovs95.event.generator.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.salomovs95.event.generator.dto.CreateEventDto;
import com.salomovs95.event.generator.dto.EventTopFiveItem;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.exception.EventCreationException;
import com.salomovs95.event.generator.exception.EventNotFoundException;
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
    if (startDate.atStartOfDay(ZoneId.systemDefault()).isBefore(LocalDate.now().atStartOfDay(ZoneId.systemDefault()))) throw new EventCreationException("Invalid event period");
    if (startDate.atStartOfDay(ZoneId.systemDefault()).isAfter(endDate.atStartOfDay(ZoneId.systemDefault()))) throw new EventCreationException("Invalid event period");

    String prettyName = dto.title().toLowerCase().replaceAll(" ", "-");
    Optional<EventEntity> eventToBe = eventRepository.findByPrettyName(prettyName);

    if (eventToBe.isPresent()) return eventToBe.get();

    EventEntity eventPrototype = new EventEntity();
    eventPrototype.setPrice(dto.price());
    eventPrototype.setTitle(dto.title());
    eventPrototype.setLocation(dto.location());
    eventPrototype.setPrettyName(prettyName);
    eventPrototype.setStartDate(startDate);
    eventPrototype.setEndDate(endDate);

    return eventRepository.save(eventPrototype);
  }

  public EventEntity findEvent(Integer eventId) throws Exception {
    EventEntity event = eventRepository.findById(eventId).orElseThrow(
      ()->new EventNotFoundException(String.format("No event was found with id {%d}", eventId))
    );

    return event;
  }

  public EventEntity findEvent(String prettyName) throws Exception {
    EventEntity event = eventRepository.findByPrettyName(prettyName).orElseThrow(
      ()->new EventNotFoundException(String.format("No event was found with prettyName {%s}", prettyName))
    );
    return event;
  }

  public List<EventTopFiveItem> listTopFiveEvents() {
    return eventRepository.getTopFiveEvents();
  }
}
