package com.salomovs95.event.generator.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateEventDto(
  String title,
  Integer price,
  String location,
  LocalDate startDate,
  LocalDate endDate,
  LocalTime startTime,
  LocalTime endTime
) {}
