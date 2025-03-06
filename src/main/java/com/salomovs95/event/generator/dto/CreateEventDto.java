package com.salomovs95.event.generator.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateEventDto(
  @NotBlank @NotNull @Size(min=10) String title,
  @NotNull @Min(1) Integer price,
  @NotBlank @NotNull @Size(min=10) String location,
  @NotNull LocalDate startDate,
  @NotNull LocalDate endDate
) {}
