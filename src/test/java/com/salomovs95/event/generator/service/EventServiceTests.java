package com.salomovs95.event.generator.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.salomovs95.event.generator.dto.CreateEventDto;
import com.salomovs95.event.generator.entity.EventEntity;
import com.salomovs95.event.generator.exception.EventCreationException;
import com.salomovs95.event.generator.repository.EventRepository;

@Tag("EVENT_TESTS")
@SpringBootTest @TestInstance(Lifecycle.PER_CLASS)
class EventServiceTests {

  @MockitoBean
  private EventRepository eMockRepository;
  private EventService eService;

  @BeforeAll
  void SETUP() {
    eService = new EventService(eMockRepository);
  }

	@Test
	void CREATE_EVENT_SUCCEEDS() {
    CreateEventDto mDto = new CreateEventDto(
      "Event Title One",
      3999,
      "Santa Luzia - Parana / Brazil",
      LocalDate.now(),
      LocalDate.now().plusDays(2),
      LocalTime.now(),
      LocalTime.now().plusHours(8)
    );
    assertDoesNotThrow(()->eService.create(mDto));
	}

  @Test
  void CREATE_EVENT_FAILS_INVALID_DTO() {
    CreateEventDto mDto = null;
    Exception exception = assertThrows(Exception.class, ()->eService.create(mDto));
    assertEquals("Invalid data provided", exception.getMessage());
  }

  @Test
  void CREATE_EVENT_FAILS_INVALID_DATE_PERIOD() {
    CreateEventDto mDto = new CreateEventDto(
      "Event Title Two",
      3999,
      "Santa Luzia - Parana / Brazil",
      LocalDate.now(),
      LocalDate.now().minusDays(2),
      LocalTime.now(),
      LocalTime.now().plusHours(5)
    );
    Exception exception = assertThrows(Exception.class, ()->eService.create(mDto));
    assertEquals("Invalid event period", exception.getMessage());
  }

  @Test
  void CREATE_EVENT_FAILS_INVALID_TIME_PERIOD() {
    CreateEventDto mDto = new CreateEventDto(
      "Event Title Three",
      3999,
      "Santa Luzia - Parana / Brazil",
      LocalDate.now(),
      LocalDate.now().plusDays(3),
      LocalTime.now(),
      LocalTime.now().minusHours(5));
    Exception exception = assertThrows(Exception.class, ()->eService.create(mDto));
    assertEquals("Invalid event duration", exception.getMessage());
  }

  @Test
  void FIND_EVENT_BY_PRETTY_NAME_SUCCEEDS() {
    when(eMockRepository.findByPrettyName(anyString())).thenReturn(Optional.of(new EventEntity(
      19884382,
      "Event Title One",
      "event-title-one",
      3999,
      "Santa Luzia - Parana / Brazil",
      LocalDate.now(),
      LocalDate.now().plusDays(3),
      LocalTime.now(),
      LocalTime.now().plusHours(6)
    )));
    assertTrue(eService.findEvent("event-title-one").isPresent());
  }

  @Test
  void FIND_EVENT_BY_PRETTY_NAME_FAILS() {
    assertTrue(eService.findEvent("event-title-one").isEmpty());
  }

  @Test
  void CREATE_PAST_EVENT_FALLS() {
    CreateEventDto mDto = new CreateEventDto(
      "title",
      9,
      "location",
      LocalDate.now().minusDays(3),
      LocalDate.now().plusDays(1),
      LocalTime.now().plusHours(1),
      LocalTime.now().plusHours(6)
    );

    EventCreationException ex = assertThrows(EventCreationException.class, ()->eService.create(mDto));
    assertEquals("Past events are not allowed", ex.getMessage());
  }

}
