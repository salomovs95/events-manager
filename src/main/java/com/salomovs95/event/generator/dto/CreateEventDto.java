package com.salomovs95.event.generator.dto;

import java.time.LocalDate;

public record CreateEventDto(
  String title,
  Integer price,
  String location,
  LocalDate startDate,
  LocalDate endDate
) {}
